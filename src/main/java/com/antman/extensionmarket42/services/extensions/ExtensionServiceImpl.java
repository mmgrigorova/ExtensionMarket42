package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
