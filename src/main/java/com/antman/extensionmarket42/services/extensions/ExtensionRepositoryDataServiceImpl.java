package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.repository.DataRefresh;
import com.antman.extensionmarket42.payload.RepositorySyncStatistics;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.GitHubDataRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExtensionRepositoryDataServiceImpl implements ExtensionRepositoryDataService {
    private static Logger logger = LoggerFactory.getLogger(ExtensionRepositoryDataServiceImpl.class);

    @Autowired
    public ExtensionRepositoryDataServiceImpl(ExtensionRepository extensionRepository, GitHubDataRepository gitHubDataRepository, ExtensionService extensionService, RemoteRepositoryService remoteRepositoryService) {
        this.extensionRepository = extensionRepository;
        this.gitHubDataRepository = gitHubDataRepository;
        this.extensionService = extensionService;
        this.remoteRepositoryService = remoteRepositoryService;
    }

    private ExtensionRepository extensionRepository;
    private GitHubDataRepository gitHubDataRepository;
    private ExtensionService extensionService;
    private RemoteRepositoryService remoteRepositoryService;

    @Override
    public RepositorySyncStatistics refreshRepositoryInfoAllActiveExtensions() {
        RepositorySyncStatistics refreshStats = new RepositorySyncStatistics();
        List<Extension> extensions = extensionRepository.findAllByActiveTrue().stream()
                .filter(extension -> !extension.getDownloadLink().equals("n/a"))
                .map(extension -> getExtensionRepositoryInformationWrapper(extension.getId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        StreamSupport
                .stream(extensionRepository.saveAll(extensions).spliterator(), false)
                .collect(Collectors.toList());

        DataRefresh dataRefresh = new DataRefresh();
        dataRefresh.setLastRefreshDate(System.currentTimeMillis());
        dataRefresh.setSuccessfulCount(refreshStats.getSuccessfullExtensions().size());
        dataRefresh.setFailedCount(refreshStats.getUnnsuccessfulExtensions().size());
        gitHubDataRepository.save(dataRefresh);
        return refreshStats;
    }

    @Override
    public Extension refreshRepositoryInfoPerExtension(Long id) throws NotFoundException, IOException {
        Extension extension = getExtensionRepositoryInformation(id);
        return extensionRepository.save(extension);
    }

    /**
     * Wrapper method which catches checked exceptions to serve streams.
     * @param extensionId
     * @return Extension with reloaded repository data
     */
    private Extension getExtensionRepositoryInformationWrapper(Long extensionId){
        try {
            logger.info("Updating GitHub data");
            return getExtensionRepositoryInformation(extensionId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return null;
    }

    private Extension getExtensionRepositoryInformation(Long extensionId) throws NotFoundException, IOException {
        Extension extension = extensionService.getById(extensionId);
        RepositoryDto repositoryDto = remoteRepositoryService.getRepositoryInfoByRepoLink(extension.getRepoLink());
        extension.setLastCommit(new java.sql.Date(repositoryDto.getLastCommit().getTime()));
        extension.setPullRequests(repositoryDto.getPullRequests());
        extension.setOpenIssues(repositoryDto.getOpenIssues());
        return extension;
    }

}
