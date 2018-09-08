package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.repository.DataRefresh;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.GitHubDataRepository;
import com.antman.extensionmarket42.utils.SystemTimeWrapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExtensionRepositoryDataServiceImpl implements ExtensionRepositoryDataService {
    private static Logger logger = LoggerFactory.getLogger(ExtensionRepositoryDataServiceImpl.class);

    private ExtensionRepository extensionRepository;
    private GitHubDataRepository gitHubDataRepository;
    private ExtensionService extensionService;
    private RemoteRepositoryService remoteRepositoryService;
    private SystemTimeWrapper systemTimeWrapper;

    @Autowired
    public ExtensionRepositoryDataServiceImpl(ExtensionRepository extensionRepository, GitHubDataRepository gitHubDataRepository, ExtensionService extensionService, RemoteRepositoryService remoteRepositoryService) {
        this.extensionRepository = extensionRepository;
        this.gitHubDataRepository = gitHubDataRepository;
        this.extensionService = extensionService;
        this.remoteRepositoryService = remoteRepositoryService;
        systemTimeWrapper = new SystemTimeWrapper();
    }


    public ExtensionRepositoryDataServiceImpl(ExtensionRepository extensionRepository, GitHubDataRepository gitHubDataRepository, ExtensionService extensionService, RemoteRepositoryService remoteRepositoryService, SystemTimeWrapper systemTimeWrapper) {
        this.extensionRepository = extensionRepository;
        this.gitHubDataRepository = gitHubDataRepository;
        this.extensionService = extensionService;
        this.remoteRepositoryService = remoteRepositoryService;
        this.systemTimeWrapper = systemTimeWrapper;
    }

    @Override
    public DataRefresh refreshRepositoryInfoAllActiveExtensions() {
        DataRefresh refreshStats = new DataRefresh();

        List<Extension> extensions = extensionRepository.findAllByActiveTrueAndPendingIs(false);

        for (Extension extension : extensions) {
            try {
                refreshRepositoryInfoPerExtension(extension.getId());
                refreshStats.addSuccessfull(extension);
            } catch (NotFoundException e) {
                logger.warn(e.getMessage(),e);
                e.printStackTrace();
            } catch (IOException e) {
                refreshStats.addFailed(extension);
                e.printStackTrace();
            }
        }

        extensionRepository.saveAll(refreshStats.getSuccessfulExtensions());

        refreshStats.setLastRefreshDate(systemTimeWrapper.currentTimeMillisSystem());
        refreshStats.setSuccessfulCount(refreshStats.getSuccessfulExtensions().size());
        refreshStats.setFailedCount(refreshStats.getFailedExtensions().size());
        gitHubDataRepository.save(refreshStats);

        return refreshStats;
    }

    @Override
    public Extension refreshRepositoryInfoPerExtension(Long id) throws NotFoundException, IOException {
        Extension extension = getExtensionRepositoryInformation(id);
        return extensionRepository.save(extension);
    }

    @Override
    public DataRefresh getLastSyncData(){
        return gitHubDataRepository.findFirstByOrderByLastRefreshDateDesc();
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
