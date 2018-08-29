package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditExtensionController {
    ExtensionService extensionService;

    @Autowired
    public  EditExtensionController(ExtensionService extensionService){
        this.extensionService = extensionService;
    }
    @GetMapping("editExtension")
    public String displayExtension(Model model){

        return "editExtension";
    }

}
