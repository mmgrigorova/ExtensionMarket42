package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;

public interface ExtensionService {
    Extension getById(long id);
    Extension save(Extension extension);
}
