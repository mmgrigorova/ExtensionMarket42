package com.antman.extensionmarket42.Extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.repository.DataRefresh;
import com.antman.extensionmarket42.payload.RepositorySyncStatistics;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.GitHubDataRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataService;
import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataServiceImpl;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.RemoteRepositoryService;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    @Before
    public void setupData(){
//        when(extensionRepository.findAllByActiveTrueAndPendingIs(false))
////                .thenReturn(Arrays.asList(
////                        new Extension()
////                ));
        extensionRepositoryDataService = new ExtensionRepositoryDataServiceImpl(extensionRepository,gitHubDataRepository,extensionService,remoteRepositoryService);
    }

    @Test
    public void test(){
        Assert.assertEquals(1,1);
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
}
