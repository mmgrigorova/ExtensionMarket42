package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.users.base.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;


//FOR THE SAKE OF TESTING
@RestController
public class AddExtensionRestController {
    private ExtensionService extensionService;

    @Autowired
    public AddExtensionRestController(ExtensionService extensionService, UserRegistrationService userRegistrationService){
        this.extensionService = extensionService;
    }

    @RequestMapping("/add")
    public String addExtension(){
        Date date = new Date(1987,4,12);
        Extension ex = new Extension("Othername","descr2","ver 2",0,"link","repo",0,0,date);

        extensionService.save(ex);


        return "add extension";
    }
}
