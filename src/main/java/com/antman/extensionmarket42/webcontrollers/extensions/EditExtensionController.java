package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller("/editExtension")
public class EditExtensionController {
    private ExtensionService extensionService;

    @Autowired
    public  EditExtensionController(ExtensionService extensionService){
        this.extensionService = extensionService;
    }

    @RequestMapping(value ="adminPanel/delete/{extensionId}",method = RequestMethod.POST)
    public String removeExtension(@PathVariable("extensionId")long extensionId){
        extensionService.removeById(extensionId);
        return "adminPanel";
    }
//    @RequestMapping("editExtension" )
//    public String removeExtension(@ModelAttribute Extension extension){
//        extensionService.removeById(extension.getId());
//
//        return "adminPanel";
//    }




}
