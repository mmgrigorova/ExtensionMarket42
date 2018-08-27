package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class ExtensionDetailsController {
    private ExtensionService extensionService;

    @Autowired
    public ExtensionDetailsController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping("extension-details/{id}")
    public ModelAndView showExtensionDetailsPage(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("extension-details");
        Extension extension = null;
        try {
            extension = extensionService.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            mav = new ModelAndView("extension-details");
            mav.addObject("errormessage", "There is no such extension");
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(extension.getDownloadLink())
                .toUriString();

        mav.addObject("fileDownloadUri", fileDownloadUri);
        mav.addObject(extension);
        return mav;
    }
}