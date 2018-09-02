package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import javassist.NotFoundException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ExtensionService {
    Extension getById(Long id) throws NotFoundException;

    Extension createNewExtension(ExtensionDto extensionDto) throws ParseException, IOException;

    int increaseDownloadCount(Long extensionId);

    List<Extension> getAll();
    List<Extension> getByName(String name);
    List<Extension> getApprovedFeatured(boolean b);
    List<Extension> getPending(boolean b);
    List<Extension> getMostPopularApproved();
    List<Extension> getByTag(String tag);
    List<Extension> getRecentlyAdded();
    void removeById(long id);

    //Sorting
    List<Extension> orderByDownloadsCount();
    List<Extension> orderByLastCommit();
    List<Extension> orderByUploadDate();
    List<Extension> orderByName();


    Extension approvePendingExtension(Long extensionId) throws NotFoundException;

    String setUniqueFileName(Extension newExtension, String extensionFileName) throws NotFoundException;

    String generateUniqueFileName(Extension newExtension, String originalFileName);
}
