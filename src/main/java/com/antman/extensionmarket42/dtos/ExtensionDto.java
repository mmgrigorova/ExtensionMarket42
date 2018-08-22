package com.antman.extensionmarket42.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExtensionDto {
    private static final String REQUIRED_MESSAGE = "This field is required";
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
}
