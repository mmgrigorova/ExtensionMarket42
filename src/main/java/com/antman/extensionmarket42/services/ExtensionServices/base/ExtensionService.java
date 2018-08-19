package com.antman.extensionmarket42.services.ExtensionServices.base;

import com.antman.extensionmarket42.models.Extension;

public interface ExtensionService {
    Extension getById(long id);
    Extension save(Extension extension);
}
