package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtensionServiceImpl implements ExtensionService {

    private final ExtensionRepository extensionRepository;

    @Autowired
    public ExtensionServiceImpl(ExtensionRepository extensionRepository) {
        this.extensionRepository = extensionRepository;
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
    public Extension save(ExtensionDto extensionDto) {
        Extension extension = new Extension();


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
    public void removeByIde(long id)
    {
        extensionRepository.deleteById(id);
    }


}
