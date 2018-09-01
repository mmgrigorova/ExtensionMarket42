package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.utils.FormChoice;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.Normalizer;
import java.util.List;

@Controller
public class DisplayExtensionsController {
    private ExtensionService extensionService;

    @Autowired
    public DisplayExtensionsController(ExtensionService extensionService){
        this.extensionService = extensionService;
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
                case "featured": extensions = extensionService.getApprovedFeatured(true);
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
    @RequestMapping(value = "adminPanel/{extensionId}", method = RequestMethod.GET)
    public ModelAndView editExtension(@PathVariable("extensionId")long extensionId) throws Exception{
        ModelAndView modelAndView = new ModelAndView("editExtension");

        Extension extension = extensionService.getById(extensionId);
        modelAndView.addObject("extension",extension);
        return modelAndView;
    }

    @GetMapping(value = "adminPanel/approve/{extensionId}")
    public ModelAndView approvePendingExtension(@PathVariable Long extensionId,
                                                RedirectAttributes redirectAttributes) throws NotFoundException {
        ModelAndView mav = new ModelAndView("redirect:/adminPanel");
        Extension extension = extensionService.approvePendingExtension(extensionId);
        redirectAttributes.addFlashAttribute(extension);
        redirectAttributes.addFlashAttribute("approvedMessage", "Extension " + extension.getName() + " has been approved");
        return mav;
    }
}

