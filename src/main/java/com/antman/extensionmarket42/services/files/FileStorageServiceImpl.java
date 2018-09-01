package com.antman.extensionmarket42.services.files;

import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.ExtensionServiceImpl;
import com.antman.extensionmarket42.services.files.base.FileStorageService;
import com.antman.extensionmarket42.utils.FileStorageProperties;
import com.antman.extensionmarket42.utils.exceptions.FileStorageException;
import com.antman.extensionmarket42.utils.exceptions.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation;
    private ExtensionService extensionService;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties, ExtensionService extensionService) {
        this.extensionService = extensionService;
        fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + fileName + " . Please try again!", e);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName, Long id) {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                extensionService.increaseDownloadCount(id);
                return resource;
            } else {
                throw new MyFileNotFoundException("File now found " + fileName);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MyFileNotFoundException("File now found " + fileName, e);
        }
    }
}
