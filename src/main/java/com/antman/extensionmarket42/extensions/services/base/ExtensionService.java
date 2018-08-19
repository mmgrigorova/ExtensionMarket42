package com.antman.extensionmarket42.extensions.services.base;

import com.antman.extensionmarket42.extensions.models.Extension;

public interface ExtensionService {
    Extension getById(long id);
    Extension save(Extension extension);
}
