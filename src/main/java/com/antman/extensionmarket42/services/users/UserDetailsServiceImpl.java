package com.antman.extensionmarket42.services.users;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.UserRole;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Service
public class UserDetailsServiceImpl implements MyUserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<User> userOptional = userRepository.findById(email);

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException(
                    "There is no user with email: " + email);
        }

        User user = userOptional.get();

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        List<GrantedAuthority> authorities = getAuthorities(user.getUserRoles());

        org.springframework.security.core.userdetails.User registered = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);

        return registered;
    }

    public UserProfile getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        UserProfile userProfile = null;

        if(userOptional.isPresent()) {
            User user  = userOptional.get();
            userProfile = user.getUserProfile();
        } else {
            throw new UsernameNotFoundException("User not found");
        }

        return userProfile;
    }

    private List<GrantedAuthority> getAuthorities(List<UserRole> userRoles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            String role = userRole.getRole();
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }


}
