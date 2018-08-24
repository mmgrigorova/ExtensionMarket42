package Extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.ExtensionServiceImpl;
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

    @Before
    public void setUp(){
        extensionService = new ExtensionServiceImpl(extensionMockRepository);
    }

    @Test
    public void getById_whenExtensionIsPresent_returnExtension(){
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
    public void getFeatured_whenExtensionIsFeatured_returnExtensions(){
        List<Extension> extensions = new ArrayList<>(3);
        Extension extensionOne = new Extension();
        Extension extensionTwo = new Extension();
        Extension extensionThree = new Extension();
        //extensionOne.setFeatured(true);
        //extensionTwo.setFeatured(true);
        extensions.add(extensionOne);
        extensions.add(extensionThree);
        extensions.add(extensionTwo);


        when(extensionMockRepository.getAllByFeaturedIs(true)).thenReturn(extensions);

        List<Extension> result = extensionService.getFeatured(true);

        assertEquals(2,result.size());

    }





}
