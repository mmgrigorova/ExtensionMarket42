package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class EditExtensionController {
    private ExtensionService extensionService;

    @Autowired
    public EditExtensionController(ExtensionService extensionService){
        this.extensionService = extensionService;
    }

    @RequestMapping(value ="adminPanel/delete/{extensionId}",method = RequestMethod.POST)
    public ModelAndView removeExtension(@PathVariable("extensionId")long extensionId, RedirectAttributes redirectAttributes)throws Exception{
        Extension extension = extensionService.getById(extensionId);
        extensionService.deactivateExtension(extensionId,false);

        ModelAndView modelAndView = new ModelAndView("redirect:/adminPanel");
        redirectAttributes.addFlashAttribute("confirmMessage", "Extension " + extension.getName() + " has been deactivated");
        return modelAndView;
    }

    @RequestMapping(value = "adminPanel/save/{extensionId}",method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute("extension") Extension extension,
                                    @PathVariable("extensionId") long extensionId,
                                    RedirectAttributes redirectAttributes) throws NotFoundException {

        extensionService.updateExtension(extensionId,extension);
        ModelAndView modelAndView = new ModelAndView("redirect:/adminPanel");
        redirectAttributes.addFlashAttribute("confirmMessage","Changes to extension " + extension.getName() + "have been saved");
        return modelAndView;
    }

}
