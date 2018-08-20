package com.antman.extensionmarket42.extensions.services;

import com.antman.extensionmarket42.extensions.models.Extension;
import com.antman.extensionmarket42.extensions.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.extensions.services.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtensionServiceImpl implements ExtensionService {

    @Autowired
    private ExtensionRepository extensionRepository;

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


}
