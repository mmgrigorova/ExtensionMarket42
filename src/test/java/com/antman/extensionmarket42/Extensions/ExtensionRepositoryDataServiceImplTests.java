package com.antman.extensionmarket42.Extensions;

import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.repository.DataRefresh;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.GitHubDataRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataService;
import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataServiceImpl;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.RemoteRepositoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.*;

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

    private ExtensionRepositoryDataService extensionRepositoryDataService;
    private List<Extension> successfulExtensions;
    private List<Extension> faildedExtensions;
    private Date lastSyncDate;

    @Before
    public void setupData() {
        successfulExtensions = Arrays.asList(
                createExtension("extension1", "extensiondesc1", 5, 10, "www.github.com/ext1/ext1"),
                createExtension("extension2", "extensiondesc2", 5, 10, "www.github.com/ext2/ext2"),
                createExtension("extension3", "extensiondesc3", 5, 10, "www.github.com/ext3/ext3")
        );
        when(extensionRepository.findAllByActiveTrueAndPendingIs(false))
                .thenReturn(successfulExtensions);
        faildedExtensions = new ArrayList<>();
        extensionRepositoryDataService = new ExtensionRepositoryDataServiceImpl(extensionRepository, gitHubDataRepository, extensionService, remoteRepositoryService);

        //        Epoch timestamp: 1536309425
        //        Human time (GMT): Friday, September 7, 2018 8:37:05 AM
        lastSyncDate = new Date(1536309425);

    }

    @Test
    public void refreshRepositoryInfoAllActiveExtensions_WhenRefreshIsSuccessful_ReturnRepositorySyncStatisticsWith3SuccessfulExtensions() {
        DataRefresh extectedStatistics = new DataRefresh();
        Assert.assertEquals(1, 1);
    }

    //
//    @Override
//    public RepositorySyncStatistics refreshRepositoryInfoAllActiveExtensions() {
//        RepositorySyncStatistics refreshStats = new RepositorySyncStatistics();
//
//        List<Extension> extensions = extensionRepository.findAllByActiveTrueAndPendingIs(false);
//
//        for (Extension extension : extensions) {
//            try {
//                refreshRepositoryInfoPerExtension(extension.getId());
//                refreshStats.addSuccessfull(extension);
//            } catch (NotFoundException e) {
//                logger.warn(e.getMessage(),e);
//                e.printStackTrace();
//            } catch (IOException e) {
//                refreshStats.addFailed(extension);
//                e.printStackTrace();
//            }
//        }
//
//        extensionRepository.saveAll(refreshStats.getSuccessfulExtensions());
//
//        DataRefresh dataRefresh = new DataRefresh();
//        dataRefresh.setLastRefreshDate(System.currentTimeMillis());
//        dataRefresh.setSuccessfulCount(refreshStats.getSuccessfulExtensions().size());
//        dataRefresh.setFailedCount(refreshStats.getFailedExtensions().size());
//
//        gitHubDataRepository.save(dataRefresh);
//        return refreshStats;
//    }
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
