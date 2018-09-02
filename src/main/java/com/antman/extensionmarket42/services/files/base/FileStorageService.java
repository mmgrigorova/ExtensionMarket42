package com.antman.extensionmarket42.services.files.base;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, Long id);
    Resource loadFileAsResource(String fileName, Long id);
}

