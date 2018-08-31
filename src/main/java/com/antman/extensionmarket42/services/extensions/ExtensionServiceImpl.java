package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.extensions.Tag;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.TagRepository;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

@Service
public class ExtensionServiceImpl implements ExtensionService {

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
        if (!extensionOptional.isPresent()){
            throw new NotFoundException("Extension has not been found");
        }
        return extensionOptional.get();
    }

    @Override
    public List<Extension> getByName(String name) {
        return extensionRepository.getAllByNameIs(name);
    }

    @Override
    public Extension createNewExtension(ExtensionDto extensionDto) throws ParseException{
        Extension extension = new Extension();

        extension.setName(extensionDto.getName());
        extension.setDescription(extensionDto.getDescription());
        extension.setVersion(extensionDto.getVersion());

        String gitHubUrl = "www.github.com";
        String repoLink = gitHubUrl + "/" + extensionDto.getRepoUser() + "/" + extensionDto.getRepoName();
        extension.setRepoLink(repoLink);

        RepositoryDto repositoryDto = null;
        try {
           repositoryDto = gitHubService.getRepositoryInfo(extensionDto.getRepoUser(), extensionDto.getRepoName());
        } catch (IOException e){
            long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1971 01:00:00").getTime() / 1000;
            repositoryDto = new RepositoryDto(0, 0, new Date(epoch));
        }
        extension.setOpenIssues(repositoryDto.getOpenIssues());
        extension.setPullRequests(repositoryDto.getPullRequests());
        extension.setLastCommit(new java.sql.Date(repositoryDto.getLastCommit().getTime()));

        extension.setDownloadLink(extensionDto.getFileName());

        extension.setUserProfile(userDetailsService.getCurrentUser());

        extension.setPending(true);
        java.sql.Date currentDate = new Date(System.currentTimeMillis());
        extension.setAddedOn(currentDate);

        Set<Tag> tags = generateTagListFromDto(extensionDto.getTags());
        extension.setTags(tags);

        return extensionRepository.save(extension);
    }

    @Override
    public int increaseDownloadCount(Long extensionId){
        Optional<Extension> optionalExtension = extensionRepository.findById(extensionId);
        Extension extension = null;

        if(optionalExtension.isPresent()){
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
        return extensionRepository.findAll();
    }

    @Override
    public List<Extension> getFeatured(boolean b) {
        return extensionRepository.getAllByFeaturedIs(b);
    }

    @Override
    public List<Extension> getPending(boolean b) {
        return extensionRepository.getAllByPendingIs(b);
    }

    @Override
    public List<Extension> getMostPopular() {
        return extensionRepository.findTop5ByOrderByDownloadsCountDesc();
    }

    @Override
    public List<Extension> getByTag(String tag) {
        return extensionRepository.findByTags_tagTitle(tag);
    }

    @Override
    public List<Extension> getRecentlyAdded() {
        return extensionRepository.findTop5ByOrderByAddedOnDesc();
    }

    @Override
    public void removeById(long id)
    {
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
    public List<Extension> orderByName(){
        return extensionRepository.findAllByOrderByName();
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
