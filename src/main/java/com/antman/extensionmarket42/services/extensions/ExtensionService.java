package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import javassist.NotFoundException;

import java.util.List;

public interface ExtensionService {
    Extension getById(Long id) throws NotFoundException;

    Extension save(ExtensionDto extensionDto);

    List<Extension> getAll();
    List<Extension> getByName(String name);
    List<Extension> getFeatured(boolean b);
    List<Extension> getPending(boolean b);
    List<Extension> getMostPopular();
    List<Extension> getByTag(String tag);
    List<Extension> getRecentlyAdded();
    void removeByIde(long id);

    //Sorting
    List<Extension> orderByDownloadsCount();
    List<Extension> orderByLastCommit();
    List<Extension> orderByUploadDate();


}
