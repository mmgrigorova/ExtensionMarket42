package com.antman.extensionmarket42.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.extensions.Tag;
import com.antman.extensionmarket42.repositories.base.ExtensionRepository;
import com.antman.extensionmarket42.repositories.base.TagRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.extensions.ExtensionServiceImpl;
import com.antman.extensionmarket42.services.extensions.RemoteRepositoryService;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import javafx.beans.binding.When;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ExtensionsServiceTests {
    @Mock
    private ExtensionRepository extensionMockRepository;

    private ExtensionService extensionService;
    private MyUserDetailsService userDetailsService;
    private RemoteRepositoryService remoteRepositoryService;
    @Mock
    private TagRepository tagRepository;

    @Before
    public void setUp() {
        extensionService = new ExtensionServiceImpl(extensionMockRepository, tagRepository, userDetailsService, remoteRepositoryService);
    }

    @Test
    public void getById_whenExtensionIsPresent_returnExtension() throws NotFoundException {
        Extension extension = ExtensionTestSetup.createExtension("testName", "description", "1.0", "repoLink",
                "link", 0, 0, 0);

        when(extensionMockRepository.findById(anyLong())).thenReturn(Optional.of(extension));

        Extension result = extensionService.getById(anyLong());

        verify(extensionMockRepository, times(1)).findById(anyLong());

        assertEquals(extension, result);

    }

    @Test(expected = javassist.NotFoundException.class)
    public void getById_whenExtensionIsNotPresent_ThrowNotFoundException() throws NotFoundException {
        extensionService.getById(-1L);
    }

    @Test
    public void getByName_whenExtensionsArePresent_returnExtensions() {
        List<Extension> extensions = new ArrayList<>();

        when(extensionMockRepository.getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase("testName")).thenReturn(extensions);

        List<Extension> result = extensionService.getByName("testName");

        verify(extensionMockRepository, times(1)).getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase("testName");

        assertEquals(extensions, result);
    }

    @Test
    public void getByUserId_whenUserProfileIdMatchesOwnerId_returnExtension() {
        List<Extension> extensions = new ArrayList<>();

        Extension extensionOne = ExtensionTestSetup.createExtension(1l);
        extensionOne.setUserProfile(new UserProfile());
        extensionOne.getUserProfile().setUserId(1L);
        extensions.add(extensionOne);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(1L);

        when(extensionMockRepository.getAllByActiveTrueAndUserProfile_UserId(1L)).thenReturn(extensions);

        List<Extension> result = extensionService.getByUserId(1L);

        assertEquals(extensions, result);
    }

    @Test
    public void getAll_whenExtensionsArePresent_returnAllActiveExtension() {
        List<Extension> extensions = new ArrayList<>();
        List<Extension> activeExtensions = new ArrayList<>();

        Extension activeExtension = new Extension();
        activeExtension.setActive(true);

        extensions.add(new Extension());
        extensions.add(new Extension());
        activeExtensions.add(activeExtension);

        when(extensionMockRepository.findAllByActiveTrueOrderByName()).thenReturn(activeExtensions);

        List<Extension> result = extensionService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    public void deactivateExtension_whenBooleanIsFalse_ThenReturnExtensionWhichIsNotActive() throws NotFoundException {
        Extension extension = new Extension();
        extension.setActive(true);
        Extension newData = new Extension();
        newData.setActive(false);

        when(extensionMockRepository.findById(1L))
                .thenReturn(Optional.of(extension));
        when(extensionMockRepository.save(any(Extension.class)))
                .thenReturn(newData);

        Extension result = extensionService.deactivateExtension(1L, false);

        Assert.assertEquals(result.isActive(), false);
    }

    @Test
    public void increaseDownloadCount_whenExtensionIsPresent_ReturnIncreasedDownloadCountByOne() {
        // Arrange
        int currentDownloadCount = 5;
        Extension extension = ExtensionTestSetup.createExtension("testName", "description", "1.0", "repoLink",
                "link", currentDownloadCount, 0, 0);
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
        verify(extensionMockRepository, times(1)).save(extension);
        Assert.assertFalse(result.isPending());
    }

    @Test
    public void generateUniqueFileName_WhenPassingValidExtension_ReturnStringInFormatId_Filename() {
        //Arrange
        String originalFilename = "initialFile.txt";

        ExtensionDto extensionDto = new ExtensionDto();
        extensionDto.setName("testextension");
        extensionDto.setVersion("3.5");

        Extension extension = new Extension();
        extension.setId(1L);

        //Act
        String result = extensionService.generateUniqueFileName(extensionDto, originalFilename);

        //Assert
        String expectedFileName = "testextension_3.5_initialFile.txt";
        Assert.assertEquals(result, expectedFileName);
    }

    @Test
    public void generateTagListFromDto_whenTagsAreProvided_ReturnSetOfTagsWithLowerCaseNonNumericCharactersRemoved() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Reflection for testing private method
        String METHOD_NAME = "generateTagListFromDto";
        Class[] parameterTypes;
        Object[] parameters;
        parameterTypes = new Class[1];
        parameterTypes[0] = java.lang.String[].class;
        Method method = extensionService.getClass().getDeclaredMethod(METHOD_NAME, parameterTypes);
        method.setAccessible(true);
        parameters = new Object[1];

        String[] tagsInput = {"testTag1", "testTagWithChars2"};

        Set<Tag> extectedTags = new HashSet<>();
        extectedTags.add(new Tag("testtag1"));
        extectedTags.add(new Tag("testtagwithchars2"));


        parameters[0] = tagsInput;
        Set<Tag> result = (Set<Tag>) method.invoke(extensionService, parameters);

        Assert.assertTrue(equalsTags(result, extectedTags));
    }

    private boolean equalsTags(Set<?> set1, Set<?> set2) {
        if (set1 == null || set2 == null) {
            return false;
        }

        if (set1.size() != set2.size()) {
            return false;
        }

        Set<String> set1Titles = set1.stream()
                .map(Object::toString)
                .collect(Collectors.toSet());

        Set<String> set2Titles = set2.stream()
                .map(Object::toString)
                .collect(Collectors.toSet());

        return set1Titles.containsAll(set2Titles);
    }

    @Test
    public void updateExtension_WhenPassingIdAndExtension_ReturnsUpdatedExtension() throws NotFoundException {

        Extension extension = ExtensionTestSetup.createExtension(1L, "Name", "Description", "Version");
        Extension newData = ExtensionTestSetup.createExtension(1L, "uName", "uDescription", "uVersion");

        when(extensionMockRepository.findById(1L))
                .thenReturn(Optional.of(extension));
        when(extensionMockRepository.save(any(Extension.class)))
                .thenReturn(newData);

        Extension result = extensionService.updateExtension(1L, newData);

        Assert.assertThat(result, samePropertyValuesAs(newData));
    }

    @Test
    public void updateExtension_WhenPassingIdAndExtensionAndFilePath_ReturnsUpdatedExtension() throws NotFoundException {

        Extension extension = ExtensionTestSetup.createExtension(1L, "Name", "Description", "Version", "path");
        Extension newData = ExtensionTestSetup.createExtension(1L, "uName", "uDescription", "uVersion", "uPath");

        when(extensionMockRepository.findById(1L))
                .thenReturn(Optional.of(extension));
        when(extensionMockRepository.save(any(Extension.class)))
                .thenReturn(newData);

        Extension result = extensionService.updateExtension(1L, newData, "uPath");

        Assert.assertNotNull(result.getDownloadLink());
        Assert.assertThat(result, samePropertyValuesAs(newData));
    }

    @Test
    public void getApprovedFeatured_WhenListOfAllExtensions_ReturnOnlyFeaturedAndApprovedExtensionsAsList() {
        Extension extension1 = ExtensionTestSetup.createExtension(1L, "extension1", "unnapporved extension1", "1.0");
        extension1.setPending(false);
        extension1.setFeatured(true);

        List<Extension> allExtensions = Arrays.asList(extension1);

        when(extensionMockRepository.getAllByActiveTrueAndFeaturedAndPending(true, false))
                .thenReturn(allExtensions);

        List<Extension> expectedExtensions = Arrays.asList(extension1);

        // Act
        List<Extension> resultExtension = extensionService.getApprovedFeatured(true);

        //Assert
        Assert.assertTrue(resultExtension.containsAll(expectedExtensions));
    }

    @Test
    public void getPending_WhenListOfExtensions_ReturnOnlyActiveAndPending(){
        List<Extension> pendingExtensions = new ArrayList<>();

        Extension pendingExtension = new Extension();
        pendingExtension.setPending(true);
        pendingExtensions.add(pendingExtension);

        when(extensionMockRepository.findAllByActiveTrueAndPendingIs(true)).thenReturn(pendingExtensions);

        List<Extension> result = extensionService.getPending(true);

        Assert.assertTrue(result.containsAll(pendingExtensions));
        assertEquals(1,result.size());
    }

    @Test
    public void getInactive_WhenListOfExtension_ReturnOnlyInactive(){
        List<Extension> inactiveExtensions = new ArrayList<>();
        Extension inactiveExtension = new Extension();
        inactiveExtension.setActive(false);
        inactiveExtensions.add(inactiveExtension);

        when(extensionMockRepository.getAllByActiveIsFalse()).thenReturn(inactiveExtensions);

        List<Extension> result = extensionService.getInactive();

        Assert.assertTrue(result.containsAll(inactiveExtensions));
        assertEquals(1,result.size());
    }

    @Test
    public void getTopFiveMostPopularApproved_WhenMostPopularRequested_ReturnFiveMostDownloadedExtensions(){
        List<Extension> extensions = Stream.generate(Extension::new).limit(5).collect(Collectors.toList());

        //TODO: how to test
    }

    @Test
    public void toggleFeaturedExtension_WhenFeaturedIsTrue_SetFeaturedFalse() throws NotFoundException{
        Extension extension = ExtensionTestSetup.createExtension(1L);
        extension.setFeatured(true);

        Extension expectedResult = ExtensionTestSetup.createExtension(1L);
        extension.setFeatured(false);

        when(extensionMockRepository.findById(1L)).thenReturn(Optional.of(extension));
        when(extensionMockRepository.save(any(Extension.class))).thenReturn(expectedResult);

        Extension result = extensionService.toggleFeaturedExtension(1L);

        assertFalse(result.isFeatured());
        assertEquals(result.isFeatured(), expectedResult.isFeatured());
    }

    @Test
    public void findAllByName_WhenExtensionsArePresent_ReturnAllMatchingName(){
        List<Extension> extensions = new ArrayList<>();
        Extension extension = ExtensionTestSetup.createExtension(1L,"testName","","1");
        extensions.add(extension);

        Page<Extension> page = new PageImpl<>(new ArrayList<>());
        PageRequest pageable = PageRequest.of(0,5);

        when(extensionMockRepository.getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase("testName",pageable)).thenReturn(page);

        Page<Extension> result = extensionService.findAllByName("testName",pageable);

        verify(extensionMockRepository, times(1)).getAllByActiveTrueAndPendingFalseAndNameContainingIgnoreCase("testName",pageable);

        assertEquals(page, result);
    }

    @Test
    public void findAll_WhenExtensionsArePresent_ReturnAll(){
        List<Extension> extensions = new ArrayList<>();
        Extension extension = ExtensionTestSetup.createExtension(1L,"testName","","1");
        extensions.add(extension);

        Page<Extension> pages = new PageImpl<>(extensions);
        PageRequest pageable = PageRequest.of(0,5);

        when(extensionMockRepository.findAllByPendingFalseAndActiveTrueOrderByName(pageable)).thenReturn(pages);

        Page<Extension> page = extensionService.findAll(pageable);

        assertEquals(1,page.getTotalElements());

    }




}
