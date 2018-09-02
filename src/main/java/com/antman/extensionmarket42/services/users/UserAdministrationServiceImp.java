package com.antman.extensionmarket42.services.users;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.users.base.UserAdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAdministrationServiceImp implements UserAdministrationService {
    private UserRepository userRepository;

    @Autowired
    public UserAdministrationServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public User getById(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("There is no such user with username: " + username);
        }
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

    @Override
    public User deactivateUser(String username) {
        User user = getById(username);
        user.setEnabled(false);
        return userRepository.save(user);
    }


}
