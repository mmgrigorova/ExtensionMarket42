package com.antman.extensionmarket42.webcontrollers.users.developers;

import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class ExtensionController {
    @GetMapping("/extension-add")
    public String showAddExtension(){
        return "extension-add";
    }


}
