package com.antman.extensionmarket42.services.UserServices.base;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserDto;

public interface UserRegistrationService {
    User createUser(UserDto userDto);
    boolean checkUserExist(String userName);
}
