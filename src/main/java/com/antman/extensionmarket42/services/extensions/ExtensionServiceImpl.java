package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.extensions.Tag;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.TagRepository;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import com.antman.extensionmarket42.utils.SystemTimeWrapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final RemoteRepositoryService remoteRepositoryService;

    @Autowired
    public ExtensionServiceImpl(ExtensionRepository extensionRepository,
                                TagRepository tagRepository,
                                MyUserDetailsService userDetailsService,
                                RemoteRepositoryService remoteRepositoryService) {
        this.extensionRepository = extensionRepository;
        this.tagRepository = tagRepository;
        this.userDetailsService = userDetailsService;
        this.remoteRepositoryService = remoteRepositoryService;
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
    public List<Extension> getByUserId(long id) {
        return extensionRepository.getAllByActiveTrueAndUserProfile_UserId(id);
    }

    @Override
    public Extension createNewExtension(ExtensionDto extensionDto) throws ParseException {
        Extension extension = new Extension();

        extension.setName(extensionDto.getName());
        extension.setDescription(extensionDto.getDescription());
        extension.setVersion(extensionDto.getVersion());

        RepositoryDto repositoryDto = null;
        try {
            repositoryDto = remoteRepositoryService.getRepositoryInfoByRepoData(extensionDto.getRepoUser(), extensionDto.getRepoName());
        } catch (IOException e) {
            logger.info("Saving extension GitHub details with default data as GitHub is unavailable at this moment");
            long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1971 01:00:00").getTime() / 1000;
            repositoryDto = new RepositoryDto(0, 0, new Date(epoch), "n/a");
        }
        extension.setRepoLink(repositoryDto.getRepoLink()); //remove string repo link
        extension.setOpenIssues(repositoryDto.getOpenIssues());
        extension.setPullRequests(repositoryDto.getPullRequests());
        extension.setLastCommit(new java.sql.Date(repositoryDto.getLastCommit().getTime()));

        extension.setDownloadLink(generateUniqueFileName(extensionDto, extensionDto.getFileName()));

        extension.setUserProfile(userDetailsService.getCurrentUser());
        extension.setActive(true);
        extension.setPending(true);
        java.sql.Date currentDate = new Date(new SystemTimeWrapper().currentTimeMillisSystem());
        extension.setAddedOn(currentDate);

        Set<Tag> tags = generateTagListFromDto(extensionDto.getTags());
        extension.setTags(tags);

        if (extensionDto.getFontAwesomeIcon() != null) {
            extension.setIcon(extensionDto.getFontAwesomeIcon());
        }
        return extensionRepository.save(extension);
    }

    @Override
    public Extension deactivateExtension(long id, boolean b) throws NotFoundException{
        Extension extension = getById(id);
        extension.setActive(b);
        extensionRepository.save(extension);
        return extension;
    }

    @Override
    public Extension updateExtension(long id, Extension extension, String filepath) throws NotFoundException {
        Extension current = getById(id);

        current.setName(extension.getName());
        current.setDescription(extension.getDescription());
        current.setVersion(extension.getVersion());

        if(filepath != null && !filepath.isEmpty())
        {
            current.setDownloadLink(filepath);
        }

        extensionRepository.save(current);
        return current;
    }

    @Override
    public Extension updateExtension(long id, Extension extension) throws NotFoundException{
        Extension current = getById(id);

        current.setName(extension.getName());
        current.setDescription(extension.getDescription());
        current.setVersion(extension.getVersion());

        extensionRepository.save(current);
        return current;
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
        return extensionRepository.findAllByActiveTrueOrderByName();
    }

    @Override
    public List<Extension> getApprovedFeatured(boolean isFeatured) {
        return extensionRepository.getAllByActiveTrueAndFeaturedAndPending(isFeatured, false);
    }

    @Override
    public List<Extension> getPending(boolean isPending) {
        return extensionRepository.findAllByActiveTrueAndPendingIs(isPending);
    }

    @Override
    public List<Extension> getInactive() {
        return extensionRepository.getAllByActiveIsFalse();
    }

    @Override
    public List<Extension> getTopFiveMostPopularApproved() {
        return extensionRepository.findTop5ByActiveTrueAndPendingOrderByDownloadsCountDesc(false);
    }

    @Override
    public List<Extension> getRecentlyAdded() {
        return extensionRepository.findTop5ByActiveTrueAndPendingOrderByAddedOnDesc(false);
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
    public Extension toggleFeaturedExtension(long extensionId) throws NotFoundException {
        Extension extension = getById(extensionId);

        if (extension.isFeatured()) {
            extension.setFeatured(false);
        } else {
            extension.setFeatured(true);
        }
        extension = extensionRepository.save(extension);
        return extension;
    }

    @Override
    public String generateUniqueFileName(ExtensionDto extensionDto, String originalFileName) {
        return extensionDto.getName() + "_" + extensionDto.getVersion() + "_" + originalFileName;
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

    @Override
    public Page<Extension> findAllByName(String name,Pageable pageable){
        return extensionRepository.getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase(name,pageable);
    }

    @Override
    public Page<Extension> findAllByTag(String name, Pageable pageable){
        return extensionRepository.findByActiveTrueAndPendingFalseAndTags_tagTitle(name,pageable);
    }

    @Override
    public Page<Extension> findAll(Pageable pageable){
        return extensionRepository.findAllByPendingFalseAndActiveTrueOrderByName(pageable);
    }

}
