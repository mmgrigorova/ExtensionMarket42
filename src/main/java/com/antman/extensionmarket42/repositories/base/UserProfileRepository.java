package com.antman.extensionmarket42.repositories.base;

import com.antman.extensionmarket42.models.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    UserProfile getByUserId(long userId);
}
