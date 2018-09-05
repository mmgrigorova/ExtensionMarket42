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
    Extension updateExtension(Extension extension);
    Extension updateExtension(long id, Extension extension);
    int increaseDownloadCount(Long extensionId);


    List<Extension> getAll();
    List<Extension> getByName(String name);
    List<Extension> getByUserId(long id);
    List<Extension> getApprovedFeatured(boolean b);
    List<Extension> getPending(boolean b);
    List<Extension> getInactive();
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
    Extension toggleFeaturedExtension(long extensionId) throws NotFoundException;

    String generateUniqueFileName(ExtensionDto extensionDto, String originalFileName);
}
