package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.extensions.Extension;

import java.util.List;

public interface ExtensionService {
    Extension getById(long id);
    Extension save(ExtensionDto extensionDto);

    Iterable<Extension> getAll();

    List<Extension> getFeatured(boolean b);
    List<Extension> getPending(boolean b);
    void removeByIde(long id);

}
