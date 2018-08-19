package com.antman.extensionmarket42.services.ExtensionServices;

import com.antman.extensionmarket42.models.Extension;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.services.ExtensionServices.base.ExtensionService;
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
