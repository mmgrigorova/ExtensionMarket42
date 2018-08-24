package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.payload.RepositoryDetails;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExtensionServiceImpl implements ExtensionService {

    private final ExtensionRepository extensionRepository;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public ExtensionServiceImpl(ExtensionRepository extensionRepository, MyUserDetailsService userDetailsService) {
        this.extensionRepository = extensionRepository;
        this.userDetailsService = userDetailsService;
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
    public Extension save(ExtensionDto extensionDto) {
        Extension extension = new Extension();

        extension.setName(extensionDto.getName());
        extension.setDescription(extensionDto.getDescription());
        extension.setVersion(extensionDto.getVersion());
        String repoLink = extensionDto.getRepoLink();
        extension.setRepoLink(repoLink);

        RepositoryDetails repoDetails = getRepositoryDetails(repoLink);
        extension.setOpenIssues(repoDetails.getOpenIssues());
        extension.setPullRequests(repoDetails.getPullRequests());
        extension.setLastCommit(repoDetails.getLastCommit());

        extension.setUserProfile(userDetailsService.getCurrentUser());

        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        extension.setAddedOn(sqlDate);

        return extensionRepository.save(extension);
    }


    private RepositoryDetails getRepositoryDetails(String repoLink) {
        //TODO replace with actual repository information
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return new RepositoryDetails(repoLink, 0,0, new java.sql.Date(date.getTime()));
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
        return extensionRepository.getAllByTags(tag);
    }

    @Override
    public List<Extension> getRecentlyAdded() {
        return extensionRepository.findTop5ByOrderByAddedOnAsc();
    }

    @Override
    public void removeByIde(long id)
    {
        extensionRepository.deleteById(id);
    }


}
