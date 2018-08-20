package com.antman.extensionmarket42.services.users;

import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.repositories.base.UserProfileRepository;
import com.antman.extensionmarket42.services.users.base.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Iterable<UserProfile> getAll() {
        return userProfileRepository.findAll();
    }
}
