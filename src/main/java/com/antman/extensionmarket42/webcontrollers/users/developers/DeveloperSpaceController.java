package com.antman.extensionmarket42.webcontrollers.users.developers;

import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.files.base.FileStorageService;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import com.antman.extensionmarket42.services.users.base.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class DeveloperSpaceController {
    private MyUserDetailsService userDetailsService;
    private ExtensionService extensionService;
    private UserProfileService userProfileService;
    private FileStorageService fileStorageService;

    @Autowired
    public DeveloperSpaceController(MyUserDetailsService userDetailsService, ExtensionService extensionService,
                                    UserProfileService userProfileService,FileStorageService fileStorageService){
        this.userDetailsService = userDetailsService;
        this.extensionService = extensionService;
        this.userProfileService = userProfileService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/developer")
    public ModelAndView showDeveloperSpace(){
        ModelAndView modelAndView = new ModelAndView("devspace");
        UserProfile userProfile =  userDetailsService.getCurrentUser();

        List<Extension> extensions = extensionService.getByUserId(userProfile.getUserId());
        modelAndView.addObject("title", "Developer Space");
        modelAndView.addObject("userProfile",userProfile);
        modelAndView.addObject("extensions",extensions);

        return modelAndView;
    }

    @RequestMapping(value = "/developer/edit/{extensionId}", method=RequestMethod.GET)
    public ModelAndView editExtension(@PathVariable("extensionId") long extensionId, RedirectAttributes redirectAttributes) throws Exception{
        Extension extension = extensionService.getById(extensionId);
        UserProfile userProfile =  userDetailsService.getCurrentUser();
        ModelAndView modelAndView;

        if(userProfile.getUserId() == extension.getUserProfile().getUserId()){
            modelAndView = new ModelAndView("editExtension");
            modelAndView.addObject("extension",extension);
            extensionService.updateExtension(extensionId,extension);
            redirectAttributes.addFlashAttribute("confirm","Changes to extension " + extension.getName() + "have been saved");
        }
        else {
             modelAndView = new ModelAndView("access-denied");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/developer/edit/save/{extensionId}",method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute("extension") Extension extension,
                                    @PathVariable("extensionId") long extensionId,
                                    @RequestParam(value = "file",required = false) MultipartFile file,
                                    RedirectAttributes redirectAttributes){
        String filename = "";
        if(!file.isEmpty()){
            //name of uploaded file
            String originalFileName = file.getOriginalFilename();
            //unique file name for each file
            String uniqueFileName = extension.getName() + "_" + extension.getVersion() + "_" + originalFileName;
            //download link for the file
            filename = fileStorageService.storeFile(file, uniqueFileName);
        }
        System.out.println(filename);
        extensionService.updateExtension(extensionId,extension,filename);

        ModelAndView modelAndView = new ModelAndView("redirect:/developer");
        redirectAttributes.addFlashAttribute("confirmMessage","Changes to extension " + extension.getName() + "have been saved");
        return modelAndView;
    }

    @RequestMapping(value ="/developer/edit/delete/{extensionId}",method = RequestMethod.POST)
    public ModelAndView removeExtension(@PathVariable("extensionId")long extensionId, RedirectAttributes redirectAttributes)throws Exception{
        Extension extension = extensionService.getById(extensionId);
        extension.setActive(false);
        extensionService.deactivateExtension(extension);

        ModelAndView modelAndView = new ModelAndView("redirect:/developer");
        redirectAttributes.addFlashAttribute("confirmMessage", "Extension " + extension.getName() + " has been deleted");
        return modelAndView;
    }

    @RequestMapping(value = "/developer/updateProfile/",method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute("UserProfile") UserProfile userProfile,
                                    RedirectAttributes redirectAttributes){
        long userId = userDetailsService.getCurrentUser().getUserId();
        userProfileService.updateUserProfile(userId,userProfile);

        ModelAndView modelAndView = new ModelAndView("redirect:/developer");
        redirectAttributes.addFlashAttribute("confirmMessage","Changes to profile have been saved");
        return modelAndView;
    }

}
