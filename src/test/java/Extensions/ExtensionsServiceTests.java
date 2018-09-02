package Extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.TagRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.ExtensionServiceImpl;
import com.antman.extensionmarket42.services.extensions.GitHubService;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class ExtensionsServiceTests {
    @Mock
    private ExtensionRepository extensionMockRepository;

    private ExtensionService extensionService;
    private MyUserDetailsService userDetailsService;
    private GitHubService gitHubService;
    private TagRepository tagRepository;

    @Before
    public void setUp(){
        extensionService = new ExtensionServiceImpl(extensionMockRepository, tagRepository, userDetailsService, gitHubService);
    }

    @Test
    public void getById_whenExtensionIsPresent_returnExtension() throws NotFoundException {
        Extension extension = ExtensionTestSetup.createExtension("testName","description","1.0","repoLink",
                "link",0,0,0);

        when(extensionMockRepository.findById(anyLong())).thenReturn(Optional.of(extension));

        Extension result = extensionService.getById(anyLong());

        verify(extensionMockRepository,times(1)).findById(anyLong());

        assertEquals(extension, result);
        
    }

    @Test
    public void getByName_whenExtensionsArePresent_returnExtensions(){
        List<Extension> extensions = new ArrayList<>();

        when(extensionMockRepository.getAllByNameIs("testName")).thenReturn(extensions);

        List<Extension> result = extensionService.getByName("testName");

        verify(extensionMockRepository,times(1)).getAllByNameIs("testName");

        assertEquals(extensions,result);
    }
    @Test
    public void getAll_whenExtensionsArePresent_returnAllExtension(){
        List<Extension> extensions = new ArrayList<>(3);
        extensions.add(new Extension());
        extensions.add(new Extension());
        extensions.add(new Extension());

        when(extensionMockRepository.findAll()).thenReturn(extensions);

        List<Extension> result = extensionService.getAll();

        assertEquals(3,result.size());
    }

    @Test
    public void increaseDownloadCount_whenExtensionIsPresent_ReturnIncreasedDownloadCountByOne(){
        // Arrange
        int currentDownloadCount = 5;
        Extension extension = ExtensionTestSetup.createExtension("testName","description","1.0","repoLink",
                "link",currentDownloadCount,0,0);
        extension.setId(5L);

        when(extensionMockRepository.findById(5L))
                .thenReturn(Optional.ofNullable(extension));
        when(extensionMockRepository.save(any(Extension.class)))
                .thenReturn(extension);

        // Act
        int newDownloadCount = extensionService.increaseDownloadCount(extension.getId());
        int expectedDownloadCount = 6;

        // Assert
        Assert.assertEquals(expectedDownloadCount, newDownloadCount);
    }

    @Test
    public void increaseDownloadCount_whenExtensionIsNotPresent_ReturnZero() {
        when(extensionMockRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        // Act
        int newDownloadCount = extensionService.increaseDownloadCount(5L);
        int expectedDownloadCount = 0;

        // Assert
        Assert.assertEquals(expectedDownloadCount, newDownloadCount);
    }

    @Test
    public void approvePendingElections_WhenExtensionIsPending_ThenReturnExtensionWhichIsNotPending() throws NotFoundException {
        //Arrange
        Extension extension = new Extension();
        extension.setPending(true);
        extension.setId(1L);

        when(extensionMockRepository.findById(extension.getId()))
                .thenReturn(Optional.ofNullable(extension));
        when(extensionMockRepository.save(any(Extension.class)))
                .thenReturn(extension);

        //Act
        Extension result = extensionService.approvePendingExtension(1L);

        //Assert
        Assert.assertEquals(result.isPending(), false);
    }

    @Test
    public void setUniqueFileName_WhenPassingValidExtension_ReturnFileNameStartingWithExtensionId() throws NotFoundException {
        //Arrange
        String initialDownloadLink = "initialFile.txt";
        Extension extension = new Extension();
        extension.setDownloadLink(initialDownloadLink);
        extension.setId(1L);

        when(extensionMockRepository.findById(extension.getId()))
                .thenReturn(Optional.ofNullable(extension));
        when(extensionMockRepository.save(any(Extension.class)))
                .thenReturn(extension);

        //Act
        String expectedFileName = "1_initialFile.txt";
        String result = extensionService.setUniqueFileName(extension, expectedFileName);

        //Assert
        Assert.assertEquals(result, expectedFileName);
    }
}
