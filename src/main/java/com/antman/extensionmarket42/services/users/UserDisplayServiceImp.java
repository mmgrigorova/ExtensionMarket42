package com.antman.extensionmarket42.services.users;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.users.base.UserDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDisplayServiceImp implements UserDisplayService {
    private UserRepository userRepository;

    @Autowired
    public UserDisplayServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.getAllByEnabled(true);
    }

    @Override
    public List<User> getAllInactiveUsers() {
        return userRepository.getAllByEnabled(false);
    }
}
