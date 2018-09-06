package com.antman.extensionmarket42.users;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.dtos.UserDto;
import com.antman.extensionmarket42.repositories.base.UserRepository;
import com.antman.extensionmarket42.services.users.UserRegistrationServiceImpl;
import com.antman.extensionmarket42.services.users.base.UserRegistrationService;
import com.antman.extensionmarket42.utils.exceptions.EmailExistsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationServiceTests {
    @Mock
    private UserRepository userMockRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    private final String USERNAME_EMAIL_PRESENT = "user111@test.com";
    private final String USERNAME_EMAIL_NOT_PRESENT = "user222@test.com";
    private UserRegistrationService userRegistrationService;
    User expected = null;

    @Before
    public void beforeTest() {

        expected = UserDataSetup.setupUser("User222", "User222", USERNAME_EMAIL_NOT_PRESENT, "123");

        when(userMockRepository.save(any(User.class)))
                .thenReturn(expected);

        when(mockPasswordEncoder.encode(anyString()))
                .thenReturn("encodedPassword");

        userRegistrationService = new UserRegistrationServiceImpl(userMockRepository, mockPasswordEncoder);
    }
    @Test(expected = EmailExistsException.class)
    public void registerNewUserAccount_WhenEmailExists_ThrowException() throws EmailExistsException {
        UserDto userDto = new UserDto(USERNAME_EMAIL_PRESENT, "User222", "123", "123", USERNAME_EMAIL_PRESENT);
        User user = UserDataSetup.setupUser("present1", "present1", USERNAME_EMAIL_PRESENT, "123");

        when(userMockRepository.findById(USERNAME_EMAIL_PRESENT))
                .thenReturn(Optional.of(user));
        userRegistrationService.registerNewUserAccount(userDto);
    }

    @Test
    public void registerNewUserAccount_WhenEmailDoesNotExists_ReturnNewUser() throws EmailExistsException {
        // ARRANGE
        UserDto userDto = new UserDto("User222", "User222", "123", "123", USERNAME_EMAIL_NOT_PRESENT);
        when(userMockRepository.findById(USERNAME_EMAIL_NOT_PRESENT))
                .thenReturn(Optional.empty());

        // ACT
        User result = userRegistrationService.registerNewUserAccount(userDto);

        // ASSERT
        Assert.assertThat(result, samePropertyValuesAs(expected));
    }
}
