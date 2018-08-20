package services.users;

import com.antman.extensionmarket42.Role;
import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.UserRole;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.users.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTests {
    @Mock
    private UserRepository userMockRepository;

    private UserDetailsService userDetailsService;

    private User user;
    private final String USERNAME_EMAIL = "user111@test.com";
    private final String USERNAME_EMAIL_NOT_PRESENT = "user222@test.com";

    @Before
    public void beforeTest() {

        when(userMockRepository.findById(USERNAME_EMAIL_NOT_PRESENT))
                .thenReturn(Optional.empty());

        userDetailsService = new UserDetailsServiceImpl(userMockRepository);
    }


    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_whenUserIsNotPresent_throwUserNotFoundException() {
        userDetailsService.loadUserByUsername(USERNAME_EMAIL_NOT_PRESENT);
    }

    @Test
    public void loadUserByUsername_whenUserIsPresent_returnUserDetails() {
        // ARRANGE
        UserProfile profile = new UserProfile();
        profile.setFirstName("Test Firstname 111");
        profile.setLastName("Test Lastname 111");
        profile.setEmail(USERNAME_EMAIL);

        UserRole role = new UserRole();
        role.setRole(String.valueOf(Role.DEV));

        List<UserRole> roles = new ArrayList<>();
        roles.add(role);

        user = new User(USERNAME_EMAIL, "123", profile);
        user.setUserRoles(roles);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("DEV"));

        org.springframework.security.core.userdetails.User expected = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);

        when(userMockRepository.findById(USERNAME_EMAIL))
                .thenReturn(Optional.ofNullable(user));

        // ACT
        UserDetails result = userDetailsService.loadUserByUsername(USERNAME_EMAIL);

        // ASSERT
        Assert.assertThat(result, samePropertyValuesAs(expected));
    }

}
