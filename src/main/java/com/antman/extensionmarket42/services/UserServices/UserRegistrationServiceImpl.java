package com.antman.extensionmarket42.services.UserServices;

import com.antman.extensionmarket42.Role;
import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserDto;
import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.UserRole;
import com.antman.extensionmarket42.repositories.base.UserProfileRepository;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.repositories.base.UserRoleRepository;
import com.antman.extensionmarket42.services.UserServices.base.UserRegistrationService;
import com.antman.extensionmarket42.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;
    private UserRoleRepository userRoleRepository;
    private UserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, UserRoleRepository userRoleRepository, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) throws EmailExistsException {
        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException("There is an account with this email address");
        }


        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(userDto.getFirstname());
        userProfile.setLastName(userDto.getLastname());
        userProfile.setEmail(userDto.getEmail());


        User user = new User();
        user.setUsername(userDto.getEmail());
        user.setPassword(encodedPassword);
        user.setEnabled(1);
        user.setUserProfile(userProfile);

        UserRole userRole = new UserRole();
        userRole.setRole(Role.DEV);

        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);
        user.setUserRoles(roles);

        User result = userRepository.save(user);
        userProfileRepository.save(userProfile);

        return result;
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByUsername(email);
        if (user != null) {
            return true;
        }

        return false;
    }


}
