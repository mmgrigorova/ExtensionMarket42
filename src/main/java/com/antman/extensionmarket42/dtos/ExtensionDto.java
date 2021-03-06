package com.antman.extensionmarket42.dtos;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExtensionDto {
    private static final String REQUIRED_MESSAGE = "This field is required";
    private static final String FILE_REQUIRED_MESSAGE = "The extension file is required";
    private static final String NAME_SIZE_MESSAGE = "Extension name should be at least two characters long";

    @NotNull
    @NotEmpty(message = REQUIRED_MESSAGE)
    @Size(min = 2, message = NAME_SIZE_MESSAGE)
    private String name;

    private String description;


    private String version;

    @NotNull
    @NotEmpty(message = REQUIRED_MESSAGE)
    private String repoUser;

    @NotNull
    @NotEmpty(message = REQUIRED_MESSAGE)
    private String repoName;

    @NotNull
    @NotEmpty(message = FILE_REQUIRED_MESSAGE)
    private MultipartFile file;

    private String fileName;

    private String fontAwesomeIcon;

    private String[] tags;

    public ExtensionDto() {
    }

    public ExtensionDto(@NotNull @NotEmpty(message = REQUIRED_MESSAGE) @Size(min = 2, message = NAME_SIZE_MESSAGE) String name, String description, String version, @NotNull @NotEmpty(message = REQUIRED_MESSAGE) String repoUser, @NotNull @NotEmpty(message = REQUIRED_MESSAGE) String repoName, @NotNull @NotEmpty(message = FILE_REQUIRED_MESSAGE) MultipartFile file, String[] tags) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.repoUser = repoUser;
        this.repoName = repoName;
        this.file = file;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRepoUser() {
        return repoUser;
    }

    public void setRepoUser(String repoUser) {
        this.repoUser = repoUser;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getFontAwesomeIcon() {
        return fontAwesomeIcon;
    }

    public void setFontAwesomeIcon(String fontAwesomeIcon) {
        this.fontAwesomeIcon = fontAwesomeIcon;
    }
}
