package com.antman.extensionmarket42.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.extensions.Tag;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.TagRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.ExtensionServiceImpl;
import com.antman.extensionmarket42.services.extensions.RemoteRepositoryService;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)

public class ExtensionServiceTestsCreateNewExtension {
    @Mock
    private RemoteRepositoryService remoteRepositoryService;
    @Mock
    private MyUserDetailsService userDetailsService;
    @Mock
    private ExtensionRepository extensionRepository;
    @Mock
    private TagRepository tagRepository;

    private ExtensionService extensionService = null;
    private Extension expectedExtension = null;
    private UserProfile userProfile;

    @Before
    public void setupTests() {
        userProfile = new UserProfile();
        userProfile.setFirstName("TestUserFirstName");
        userProfile.setLastName("TestUserLastName");
        userProfile.setEmail("testuser@email.com");

        Mockito.when(userDetailsService.getCurrentUser())
                .thenReturn(userProfile);

    }

    @Test
    public void createNewExtension_WhenExtensionFromDTOIsCorrect_returnExtension() throws IOException, ParseException {
        // Arrange
        //        Epoch timestamp: 1501585200
        //        Human time (GMT): Tuesday, August 1, 2017 11:00:00 AM
        Date addedOn = new Date(1501585200);

        //        Epoch timestamp: 1533121200
        //        Human time (GMT): Wednesday, August 1, 2018 11:00:00 AM
        Date lastCommit = new Date(1533121200);


        expectedExtension = new Extension("Expected Name", "Expected Description", "1.0", 4, "testFile.txt",
                "www.github.com/testuser/testrepo", 5, 325, lastCommit, userProfile, null, true, false, null, addedOn);
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("testTag1"));
        expectedTags.add(new Tag("testTag2"));
        expectedExtension.setTags(expectedTags);

        Mockito.when(extensionRepository.save(any(Extension.class)))
                .thenReturn(expectedExtension);

        RepositoryDto repositoryDto = new RepositoryDto(5, 325, lastCommit, "www.github.com/testuser/testrepo");
        Mockito.when(remoteRepositoryService.getRepositoryInfoByRepoData("testuser", "testrepo"))
                .thenReturn(repositoryDto);

        extensionService = new ExtensionServiceImpl(extensionRepository, tagRepository, userDetailsService, remoteRepositoryService);

        byte[] content = new byte[0];
        MultipartFile mockFile = new MockMultipartFile("testFile.txt", content);
        String[] tags = new String[]{"testTag1", "testTag2"};
        ExtensionDto extensionDto = new ExtensionDto("Expected Name", "Expected Description", "1.0", "testuser", "testrepo", mockFile, tags);

        // Act
        Extension newExtension = extensionService.createNewExtension(extensionDto);

        // Assert
        verify(remoteRepositoryService,times(1)).getRepositoryInfoByRepoData("testuser", "testrepo");
        Assert.assertThat(expectedExtension, samePropertyValuesAs(newExtension));
    }
}
