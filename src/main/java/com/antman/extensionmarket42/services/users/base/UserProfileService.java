package com.antman.extensionmarket42.services.users.base;

import com.antman.extensionmarket42.models.UserProfile;

public interface UserProfileService {
    void updateUserProfile(long id,UserProfile userProfile);
}
