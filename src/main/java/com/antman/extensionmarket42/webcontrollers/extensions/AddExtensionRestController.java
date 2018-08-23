package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.users.base.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;


//FOR THE SAKE OF TESTING
@RestController
public class AddExtensionRestController {
    private ExtensionService extensionService;

    @Autowired
    public AddExtensionRestController(ExtensionService extensionService, UserRegistrationService userRegistrationService){
        this.extensionService = extensionService;
    }

    @GetMapping("extension-add")
    public String showAddExtension(){
        return "extension-add";
    }

    @PostMapping("extension-add")
    public ModelAndView addExtension(@RequestParam("extensionDto") ExtensionDto extensionDto,
                                     RedirectAttributes redirectAttributes,
                                     Errors errors){
        ModelAndView mav = null;

        if(errors.hasErrors()){
            mav = new ModelAndView("extension-add");
            mav.addObject("errors", errors);
            return mav;
        }

        Extension newExtension = extensionService.save(extensionDto);
        redirectAttributes.addAttribute("id", newExtension.getId())
                .addFlashAttribute("message", "Extension created!");

        mav = new ModelAndView("redirect:/accounts/{id}");

        return mav;
    }
}
