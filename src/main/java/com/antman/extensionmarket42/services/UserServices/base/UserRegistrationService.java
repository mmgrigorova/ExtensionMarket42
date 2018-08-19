package com.antman.extensionmarket42.services.UserServices.base;

import com.antman.extensionmarket42.models.User;

public interface UserRegistrationService {
    User createUser(User user);
    boolean checkUserExist(String userName);
}
