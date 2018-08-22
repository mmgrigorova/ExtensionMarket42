package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DisplayExtensionsController {
    private ExtensionService extensionService;

    @Autowired
    public DisplayExtensionsController(ExtensionService extensionService){
        this.extensionService = extensionService;
    }

    @RequestMapping("adminPanel")
        public String getAllExtensions(Model model){
            Iterable<Extension> extensions = extensionService.getRecentlyAdded();
            model.addAttribute("extensions",extensions);
            model.addAttribute("choice");
           return "adminPanel";
        }
//    @RequestMapping("adminPanel")
//    public String getPendingExtensions(Model model){
//        Iterable<Extension> extensions = extensionService.getPending(true);
//        model.addAttribute("extensions",extensions);
//
//        return "adminPanel";
//    }
//    @RequestMapping("adminPanel")
//    public String getFeaturedExtensions(Model model){
//        Iterable<Extension> extensions = extensionService.getFeatured(true);
//        model.addAttribute("extensions",extensions);
//
//        return "adminPanel";
//    }
}


