package com.antman.extensionmarket42.services.users.base;

import com.antman.extensionmarket42.models.User;

import java.util.List;

public interface UserAdministrationService {
    User getById(String username);
    Iterable<User> getAll();
    List<User> getAllActiveUsers();
    List<User> getAllInactiveUsers();
    User deactivateUser(String username);
}
