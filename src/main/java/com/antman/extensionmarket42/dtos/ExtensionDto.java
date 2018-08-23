package com.antman.extensionmarket42.dtos;

import com.antman.extensionmarket42.models.extensions.Tag;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    private String repoLink;

    @NotNull
    @NotEmpty(message = FILE_REQUIRED_MESSAGE)
    private MultipartFile file;

    private List<Tag> tags;

    public ExtensionDto(@NotNull @NotEmpty(message = REQUIRED_MESSAGE) @Size(min = 2, message = NAME_SIZE_MESSAGE) String name, String description, String version, @NotNull @NotEmpty(message = REQUIRED_MESSAGE) String repoLink, @NotNull @NotEmpty(message = FILE_REQUIRED_MESSAGE) MultipartFile file, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.repoLink = repoLink;
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

    public String getRepoLink() {
        return repoLink;
    }

    public void setRepoLink(String repoLink) {
        this.repoLink = repoLink;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
