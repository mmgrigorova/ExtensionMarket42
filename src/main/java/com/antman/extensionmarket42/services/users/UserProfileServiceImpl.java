package com.antman.extensionmarket42.services.users;

import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.repositories.base.UserProfileRepository;
import com.antman.extensionmarket42.services.users.base.UserProfileService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void updateUserProfile(long id,UserProfile userProfile) {

        UserProfile tempUser = userProfileRepository.getByUserId(id);
        tempUser.setFirstName(userProfile.getFirstName());
        tempUser.setLastName(userProfile.getLastName());
        tempUser.setEmail(userProfile.getEmail());

        userProfileRepository.save(tempUser);
    }
}
