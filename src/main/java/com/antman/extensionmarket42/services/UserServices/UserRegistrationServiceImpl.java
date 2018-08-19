package com.antman.extensionmarket42.services.UserServices;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.UserServices.base.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private UserRepository userRepository;
    private UserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;

    public UserRegistrationServiceImpl(UserRepository userRepository, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {

        String username = user.getUsername();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        org.springframework.security.core.userdetails.User newUser;
        newUser = new org.springframework.security.core.userdetails.User(
                username,
                encodedPassword,
                true,
                true,
                true,
                true,
                authorities
        );

        userDetailsManager.createUser(newUser);

        return user;
    }

    @Override
    public boolean checkUserExist(String userName) {
        return userDetailsManager.userExists(userName);
    }


}
