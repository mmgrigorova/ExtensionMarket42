package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.utils.FormChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Controller
public class DisplayExtensionsController {
    private ExtensionService extensionService;

    @Autowired
    public DisplayExtensionsController(ExtensionService extensionService){
        this.extensionService = extensionService;
    }

    @GetMapping("extension-details/{id}")
    public ModelAndView showExtensionDetailsPage(@PathVariable("id") Long id){
        ModelAndView mav = new ModelAndView("extension-details");
        Extension extension = null;
        try {
            extension = extensionService.getById(id);
        }catch (Exception e) {
            e.printStackTrace();
            mav = new ModelAndView("extension-details");
            mav.addObject("errormessage","There is no such extension" );
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(extension.getDownloadLink())
                .toUriString();

        mav.addObject("fileDownloadUri", fileDownloadUri);
        mav.addObject(extension);
        return mav;
    }
    @GetMapping("adminPanel")
    public String getAdminPage(Model model){
        List<Extension> extensions = extensionService.getRecentlyAdded();
        model.addAttribute("extensions",extensions);
        model.addAttribute("formChoice",new FormChoice());
        return "adminPanel";
    }
    @PostMapping("adminPanel")
    public String getExtensions(@ModelAttribute FormChoice formChoice, Model model){
        List<Extension> extensions = null;
            switch (formChoice.getParam()){
                case "extensions": extensions = extensionService.getAll();
                    break;
                case "featured": extensions = extensionService.getFeatured(true);
                    break;
                case "pending": extensions = extensionService.getPending(true);
                    break;
                case "users": extensions = extensionService.getByTag("Java");
                default:
                    break;
            }
        model.addAttribute("extensions", extensions);
        return "adminPanel";
    }
}

