package com.antman.extensionmarket42.services.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ExtensionService {
    Extension getById(Long id) throws NotFoundException;

    Extension createNewExtension(ExtensionDto extensionDto) throws ParseException, IOException;
    Extension deactivateExtension(long id,boolean b) throws NotFoundException;
    Extension updateExtension(long id, Extension extension) throws NotFoundException;
    Extension updateExtension(long id, Extension extension, String path) throws NotFoundException;
    int increaseDownloadCount(Long extensionId);

    List<Extension> getAll();
    List<Extension> getByName(String name);
    List<Extension> getByUserId(long id);
    List<Extension> getApprovedFeatured(boolean b);

    List<Extension> getPending(boolean b);
    List<Extension> getInactive();
    List<Extension> getTopFiveMostPopularApproved();
    List<Extension> getRecentlyAdded();

    Extension approvePendingExtension(Long extensionId) throws NotFoundException;
    Extension toggleFeaturedExtension(long extensionId) throws NotFoundException;

    String generateUniqueFileName(ExtensionDto extensionDto, String originalFileName);

    Page<Extension> findAllByName(String name,Pageable pageable);
    Page<Extension> findAll(Pageable pageable);
    Page<Extension> findAllByTag(String name,Pageable pageable);

}
