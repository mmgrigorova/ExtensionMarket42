package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class EditExtensionController {
    private ExtensionService extensionService;

    @Autowired
    public EditExtensionController(ExtensionService extensionService){
        this.extensionService = extensionService;
    }

    @RequestMapping(value ="adminPanel/delete/{extensionId}",method = RequestMethod.POST)
    public String removeExtension(@PathVariable("extensionId")long extensionId)throws Exception{
        Extension extension = extensionService.getById(extensionId);
        extension.setActive(false);
        extensionService.updateExtension(extension);
        return "redirect:/adminPanel";
    }

    @RequestMapping(value = "adminPanel/save",method = RequestMethod.POST)
    public String saveChanges(@ModelAttribute("extension") Extension extension){
        System.out.println(extension.getName());
        //extensionService.updateExtension(extension); TODo: fix
        return "redirect:/adminPanel";
    }

}
