package com.antman.extensionmarket42.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
    private static final String REQUIRED_MESSAGE = "This field is required";
    private static final String NAME_SIZE_MESSAGE = "Name should be at least two characters long";

    @NotNull
    @NotEmpty(message = REQUIRED_MESSAGE)
    @Size(min = 2, message = NAME_SIZE_MESSAGE)
    private String firstname;

    @NotNull
    @NotEmpty(message = REQUIRED_MESSAGE)
    @Size(min = 2, message = NAME_SIZE_MESSAGE)
    private String lastname;

    @NotNull
    @NotEmpty(message = REQUIRED_MESSAGE)
    private String password;

    @NotNull
    @NotEmpty(message = REQUIRED_MESSAGE)
    private String matchingPassword;

    @NotNull
    @NotEmpty(message = REQUIRED_MESSAGE)
    @Email
    private String email;

    public UserDto() {
    }

    public UserDto(@NotNull @NotEmpty String firstname, @NotNull @NotEmpty String lastname, @NotNull @NotEmpty String password, String matchingPassword, @NotNull @NotEmpty String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
