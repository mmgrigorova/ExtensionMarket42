package com.antman.extensionmarket42.services.users;

import com.antman.extensionmarket42.Role;
import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserDto;
import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.UserRole;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.users.base.UserRegistrationService;
import com.antman.extensionmarket42.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
        userRole.setRole(String.valueOf(Role.DEV));

        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);
        user.setUserRoles(roles);

        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        Optional<User> userOptional = userRepository.findById(email);
        if (userOptional.isPresent()) {
           return true;
        } else {
            return false;
        }
    }


}
