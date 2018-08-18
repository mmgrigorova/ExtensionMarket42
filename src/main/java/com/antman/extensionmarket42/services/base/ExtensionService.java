package com.antman.extensionmarket42.services.base;

import com.antman.extensionmarket42.models.Extension;

import java.util.List;

public interface ExtensionService {
    Extension getById(int id);
    Extension save(Extension extension);
}
