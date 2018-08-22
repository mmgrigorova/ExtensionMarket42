package Extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.ExtensionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void getById(long id){
        Extension extension = new E
    }

}
