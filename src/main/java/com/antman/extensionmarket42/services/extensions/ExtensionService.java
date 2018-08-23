package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;

import java.util.List;

public interface ExtensionService {
    Extension getById(long id);
    Extension save(Extension extension);

    List<Extension> getAll();
    List<Extension> getByName(String name);
    List<Extension> getFeatured(boolean b);
    List<Extension> getPending(boolean b);
    List<Extension> getMostPopular();
    List<Extension> getRecentlyAdded();
    void removeByIde(long id);

}
