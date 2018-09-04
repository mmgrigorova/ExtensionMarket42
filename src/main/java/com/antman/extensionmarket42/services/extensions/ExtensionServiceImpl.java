package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.extensions.Tag;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.TagRepository;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ExtensionServiceImpl implements ExtensionService {
    private static Logger logger = LoggerFactory.getLogger(ExtensionServiceImpl.class);
    private final ExtensionRepository extensionRepository;
    private final TagRepository tagRepository;
    private final MyUserDetailsService userDetailsService;
    private final GitHubService gitHubService;

    @Autowired
    public ExtensionServiceImpl(ExtensionRepository extensionRepository,
                                TagRepository tagRepository,
                                MyUserDetailsService userDetailsService,
                                GitHubService gitHubService) {
        this.extensionRepository = extensionRepository;
        this.tagRepository = tagRepository;
        this.userDetailsService = userDetailsService;
        this.gitHubService = gitHubService;
    }

    @Override
    public Extension getById(Long id) throws NotFoundException {
        Optional<Extension> extensionOptional = extensionRepository.findById(id);
        if (!extensionOptional.isPresent()) {
            throw new NotFoundException("Extension has not been found");
        }
        return extensionOptional.get();
    }

    @Override
    public List<Extension> getByName(String name) {
        return extensionRepository.getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase(name);
    }

    @Override
    public List<Extension> getByUserId(long id){
        return extensionRepository.getAllByActiveTrueAndUserProfile_UserId(id);
    }

    @Override
    public Extension createNewExtension(ExtensionDto extensionDto) throws ParseException {
        Extension extension = new Extension();

        extension.setName(extensionDto.getName());
        extension.setDescription(extensionDto.getDescription());
        extension.setVersion(extensionDto.getVersion());

        String gitHubUrl = "www.github.com"; //da se izvadi kato konstanta
        String repoLink = gitHubUrl + "/" + extensionDto.getRepoUser() + "/" + extensionDto.getRepoName();
        extension.setRepoLink(repoLink); //remove string repo link

        RepositoryDto repositoryDto = null;
        try {
            repositoryDto = gitHubService.getRepositoryInfo(extensionDto.getRepoUser(), extensionDto.getRepoName());
        } catch (IOException e) {
            logger.info("Saving extension GitHub details with default data as GitHub is unavailable at this moment");
            long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1971 01:00:00").getTime() / 1000;
            repositoryDto = new RepositoryDto(0, 0, new Date(epoch));
        }
        extension.setOpenIssues(repositoryDto.getOpenIssues());
        extension.setPullRequests(repositoryDto.getPullRequests());
        extension.setLastCommit(new java.sql.Date(repositoryDto.getLastCommit().getTime()));

        extension.setDownloadLink(generateUniqueFileName(extensionDto, extensionDto.getFileName()));

        extension.setUserProfile(userDetailsService.getCurrentUser());
        extension.setActive(true);
        extension.setPending(true);
        java.sql.Date currentDate = new Date(System.currentTimeMillis());
        extension.setAddedOn(currentDate);

        Set<Tag> tags = generateTagListFromDto(extensionDto.getTags());
        extension.setTags(tags);

        if (extensionDto.getFontAwesomeIcon() != null) {
            extension.setIcon(extensionDto.getFontAwesomeIcon());
        }
        return extensionRepository.save(extension);
    }

    @Override
    public Extension updateExtension(Extension extension){
        extensionRepository.save(extension);
        return extension;
    }
    @Override
    public Extension updateExtension(long id,Extension extension){
        Extension current = extensionRepository.getById(id);

        current.setName(extension.getName());
        current.setDescription(extension.getDescription());
        current.setVersion(extension.getVersion());
        current.setDownloadLink(extension.getDownloadLink());

        extensionRepository.save(current);
        return extension;
    }

    @Override
    public int increaseDownloadCount(Long extensionId) {
        Optional<Extension> optionalExtension = extensionRepository.findById(extensionId);
        Extension extension = null;

        if (optionalExtension.isPresent()) {
            extension = optionalExtension.get();
            int downloadCount = extension.getDownloadsCount();
            downloadCount += 1;
            extension.setDownloadsCount(downloadCount);
            extensionRepository.save(extension);
            return downloadCount;
        } else {
            return 0;
        }
    }

    @Override
    public List<Extension> getAll() {
        return extensionRepository.findAllByActiveTrue();
    }

    @Override
    public List<Extension> getApprovedFeatured(boolean b) {
        return extensionRepository.getAllByActiveTrueAndFeaturedAndPending(true, false);
    }

    @Override
    public List<Extension> getPending(boolean b) {
        return extensionRepository.findAllByActiveTrueAndPendingIs(b);
    }
    @Override
    public List<Extension> getInactive()
    {
        return extensionRepository.getAllByActiveIsFalse();
    }

    @Override
    public List<Extension> getMostPopularApproved() {
        return extensionRepository.findTop5ByActiveTrueAndPendingOrderByDownloadsCountDesc(false);
    }

    @Override
    public List<Extension> getByTag(String tag) {
        return extensionRepository.findByTags_tagTitle(tag);
    }

    @Override
    public List<Extension> getRecentlyAdded() {
        return extensionRepository.findTop5ByActiveTrueAndPendingOrderByAddedOnDesc(false);
    }

    @Override
    public void removeById(long id) {
        extensionRepository.deleteById(id);
    }

    @Override
    public List<Extension> orderByDownloadsCount() {
        return extensionRepository.findAllByOrderByDownloadsCountDesc();
    }

    @Override
    public List<Extension> orderByLastCommit() {
        return extensionRepository.findAllByOrderByLastCommitDesc();
    }

    @Override
    public List<Extension> orderByUploadDate() {
        return extensionRepository.findAllByOrderByAddedOnDesc();
    }

    @Override
    public List<Extension> orderByName() {
        return extensionRepository.findAllByOrderByName();
    }

    @Override
    public Extension approvePendingExtension(Long extensionId) throws NotFoundException {
        Extension extension = getById(extensionId);

        if (extension.isPending()) {
            extension.setPending(false);
            extension = extensionRepository.save(extension);
        }

        return extension;
    }

    @Override
    public Extension toggleFeaturedExtension(long extensionId) throws  NotFoundException{
        Extension extension = getById(extensionId);

        if(extension.isFeatured())
        {
            extension.setFeatured(false);
        }
        else {
            extension.setFeatured(true);
        }
        extension = extensionRepository.save(extension);
        return extension;
    }

    @Override
    public String generateUniqueFileName(ExtensionDto extensionDto, String originalFileName) {
        return extensionDto.getName() + "_" + extensionDto.getVersion() + "_" +  originalFileName;
    }

    private Set<Tag> generateTagListFromDto(String[] tagNames) {
        Set<Tag> tags = new HashSet<>();

        for (String tagName : tagNames) {
            // Replace any non-numeric characters
            String tag = tagName.replaceAll("\\W", "").toLowerCase();

            Optional<Tag> optionalTag = tagRepository.findTagByTagTitle(tag);
            if (optionalTag.isPresent()) {
                tags.add(optionalTag.get());
            } else {
                tags.add(new Tag(tag));
            }
        }
        return tags;
    }
}
