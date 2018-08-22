package com.antman.extensionmarket42.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "images")
public class ImageStorageProperties {
    private String imageUploadDir;

    public String getImageUploadDir() {
        return imageUploadDir;
    }

    public void setImageUploadDir(String imageUploadDir) {
        this.imageUploadDir = imageUploadDir;
    }
}
