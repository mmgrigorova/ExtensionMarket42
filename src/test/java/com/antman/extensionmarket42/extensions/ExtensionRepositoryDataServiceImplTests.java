package com.antman.extensionmarket42.extensions;

import com.antman.extensionmarket42.dtos.RepositoryDto;
import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.repository.DataRefresh;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.GitHubDataRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataService;
import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataServiceImpl;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.RemoteRepositoryService;
import com.antman.extensionmarket42.utils.SystemTimeWrapper;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExtensionRepositoryDataServiceImplTests {
    @Mock
    private ExtensionRepository extensionRepository;
    @Mock
    private GitHubDataRepository gitHubDataRepository;
    @Mock
    private ExtensionService extensionService;
    @Mock
    private RemoteRepositoryService remoteRepositoryService;
    @Mock
    SystemTimeWrapper systemTimeWrapperMock;

    private ExtensionRepositoryDataService extensionRepositoryDataService;
    private List<Extension> successfulExtensions;
    private List<Extension> faildedExtensions;
    private Timestamp lastSyncDate;

    @Before
    public void setupData() {
        when(systemTimeWrapperMock.currentTimeMillisSystem())
                .thenReturn(1536330057650L);

        // Human time (GMT): Friday, September 7, 2018 8:37:05 AM
        lastSyncDate = new Timestamp(1536330057650L);

    }


    @Test
    public void refreshRepositoryInfoAllActiveExtensions_WhenRefreshIsSuccessful_ReturnRepositorySyncStatisticsWith3SuccessfulExtensions() throws NotFoundException, IOException {
        // Arrange
        Extension extension1 = createExtension("extension1", "extensiondesc1", 5, 10, "www.github.com/ext1/ext1");
        extension1.setId(1L);
        Extension extension2 = createExtension("extension2", "extensiondesc2", 5, 10, "www.github.com/ext2/ext2");
        extension2.setId(2L);

        successfulExtensions = Arrays.asList(extension1, extension2);

        when(extensionRepository.findAllByActiveTrueAndPendingIs(false))
                .thenReturn(successfulExtensions);

        when(extensionService.getById(1L))
                .thenReturn(extension1);
        when(extensionService.getById(2L))
                .thenReturn(extension2);

        RepositoryDto repositoryDto1 = new RepositoryDto(6, 15, lastSyncDate, extension1.getRepoLink());
        repositoryDto1.setLastCommit(lastSyncDate);
        RepositoryDto repositoryDto2 = new RepositoryDto(12, 30, lastSyncDate, extension2.getRepoLink());
        repositoryDto2.setLastCommit(lastSyncDate);

        when(remoteRepositoryService.getRepositoryInfoByRepoLink(extension1.getRepoLink()))
                .thenReturn(repositoryDto1);
        when(remoteRepositoryService.getRepositoryInfoByRepoLink(extension2.getRepoLink()))
                .thenReturn(repositoryDto2);

        faildedExtensions = new ArrayList<>();
        extensionRepositoryDataService = new ExtensionRepositoryDataServiceImpl(extensionRepository, gitHubDataRepository, extensionService, remoteRepositoryService, systemTimeWrapperMock);

        DataRefresh extectedStatistics = new DataRefresh();
        extectedStatistics.setId(1);
        Timestamp timestamp = new java.sql.Timestamp(1536330057650L);
        extectedStatistics.setLastRefreshDate(timestamp);
        extectedStatistics.setSuccessfulCount(2);
        extectedStatistics.setSuccessfulExtensions(successfulExtensions);


        // Act
        DataRefresh resultStatistics = extensionRepositoryDataService.refreshRepositoryInfoAllActiveExtensions();
        resultStatistics.setId(1);

        // Assert
        Assert.assertThat(resultStatistics, samePropertyValuesAs(extectedStatistics));
    }

    @Test
    public void refreshRepositoryInfoPerExtension_whenExtensionIsPresentAndGitHubIsAvailable_ReturnUpdatedInformation() throws NotFoundException, IOException {
        // Arrange
        Extension expectedExtension = createExtension("extension1", "extensiondesc1", 5, 10, "www.github.com/ext1/ext1");
        expectedExtension.setId(1L);

        successfulExtensions = Collections.singletonList(expectedExtension);

        when(extensionRepository.save(any(Extension.class)))
                .thenReturn(expectedExtension);

        when(extensionService.getById(1L))
                .thenReturn(expectedExtension);

        RepositoryDto repositoryDto1 = new RepositoryDto(6, 15, lastSyncDate, expectedExtension.getRepoLink());
        repositoryDto1.setLastCommit(lastSyncDate);

        when(remoteRepositoryService.getRepositoryInfoByRepoLink(expectedExtension.getRepoLink()))
                .thenReturn(repositoryDto1);

        faildedExtensions = new ArrayList<>();
        extensionRepositoryDataService = new ExtensionRepositoryDataServiceImpl(extensionRepository, gitHubDataRepository, extensionService, remoteRepositoryService, systemTimeWrapperMock);

        // Act
        Extension resultExtension = extensionRepositoryDataService.refreshRepositoryInfoPerExtension(1L);

        // Assert
        Assert.assertThat(expectedExtension, samePropertyValuesAs(resultExtension));
    }

    @Test
    public void getLastSyncData_whenDataRefreshIsAvailable_ReturnMostRecentDataRefreshInformation() {
        //Arrange
        DataRefresh mostRecentRefresh = new DataRefresh();
        mostRecentRefresh.setId(1L);
        mostRecentRefresh.setSuccessfulCount(2);
        mostRecentRefresh.setFailedCount(1);
        mostRecentRefresh.setLastRefreshDate(lastSyncDate);

        when(gitHubDataRepository.findFirstByOrderByLastRefreshDateDesc())
                .thenReturn(mostRecentRefresh);

        //Act
        DataRefresh resultDataRefresh = getLastSyncData();

        //Assert
        Assert.assertThat(mostRecentRefresh, samePropertyValuesAs(resultDataRefresh));
    }

    @Test
    public void getLastSyncData_whenDataRefreshIsNotAvailable_ReturnNull() {
        //Arrange
        when(gitHubDataRepository.findFirstByOrderByLastRefreshDateDesc())
                .thenReturn(null);

        //Act
        DataRefresh resultDataRefresh = getLastSyncData();

        //Assert
        Assert.assertNull(resultDataRefresh);
    }

    public DataRefresh getLastSyncData() {
        return gitHubDataRepository.findFirstByOrderByLastRefreshDateDesc();
    }

    private Extension createExtension(String extensionName, String extensionDesc, int openIssues, int pullRequests, String repoLink) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("TestUserFirstName");
        userProfile.setLastName("TestUserLastName");
        userProfile.setEmail("testuser@email.com");

        // Arrange
        //        Epoch timestamp: 1501585200
        //        Human time (GMT): Tuesday, August 1, 2017 11:00:00 AM
        Date addedOn = new Date(1501585200);

        //        Epoch timestamp: 1533121200
        //        Human time (GMT): Wednesday, August 1, 2018 11:00:00 AM
        Date lastCommit = new Date(1533121200);

        return new Extension(extensionName, extensionDesc, "1.0", 4, "testFile.txt",
                repoLink, openIssues, pullRequests, lastCommit, userProfile, null, true, false, null, addedOn);
    }
}
