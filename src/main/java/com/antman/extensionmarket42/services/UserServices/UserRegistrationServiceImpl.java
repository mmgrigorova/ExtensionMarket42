package com.antman.extensionmarket42.services.UserServices;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.UserServices.base.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private UserRepository userRepository;
    private UserDetailsManager userDetailsManager;

    @Autowired
    public UserRegistrationServiceImpl(UserRepository userRepository, UserDetailsManager userDetailsManager) {
        this.userRepository = userRepository;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean checkUserExist(String userName) {
        return userDetailsManager.userExists(userName);
    }


}
