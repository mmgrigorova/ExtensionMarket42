package com.antman.extensionmarket42.services.users.base;

import com.antman.extensionmarket42.models.UserProfile;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {
    UserProfile getCurrentUser();
}
