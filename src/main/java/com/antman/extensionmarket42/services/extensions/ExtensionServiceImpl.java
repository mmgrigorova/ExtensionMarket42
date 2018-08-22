package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtensionServiceImpl implements ExtensionService {
    private ExtensionRepository extensionRepository;

    @Autowired
    public ExtensionServiceImpl(ExtensionRepository extensionRepository){
        this.extensionRepository = extensionRepository;
    }

    @Override
    public Extension getById(long id) {
        long s = 1;
        return extensionRepository.findById(s).get();
    }

    @Override
    public Extension save(Extension extension) {

        return extensionRepository.save(extension);
    }

    @Override
    public Iterable<Extension> getAll() {
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
    public List<Extension> getRecentlyAdded() {
        return extensionRepository.findTop5ByOrderByAddedOnAsc();
    }

    @Override
    public void removeByIde(long id)
    {
        extensionRepository.deleteById(id);
    }


}
