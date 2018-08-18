package com.antman.extensionmarket42.services.UserServices;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.UserServices.base.UserRegistrationService;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private UserRepository userRepository;

    public UserRegistrationServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
