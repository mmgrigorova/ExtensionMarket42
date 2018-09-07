package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataService;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DisplayExtensionsController {
    private ExtensionService extensionService;
    private ExtensionRepositoryDataService gitHubService;


    @Autowired
    public DisplayExtensionsController(ExtensionService extensionService,
                                       ExtensionRepositoryDataService gitHubService){
        this.extensionService = extensionService;
        this.gitHubService = gitHubService;
    }

    @GetMapping("adminPanel")
    public ModelAndView getAdminPage(@ModelAttribute("choice")  String choice){
        List<Extension> extensions = null;
        ModelAndView modelAndView = new ModelAndView("adminPanel");
        modelAndView.addObject("repoData", gitHubService.getLastSyncData());
        if (choice == null || choice.isEmpty()) {
            extensions = extensionService.getAll();
        }
        else {
            switch (choice) {
                case "all":
                    extensions = extensionService.getAll();
                    break;
                case "featured":
                    extensions = extensionService.getApprovedFeatured(true);
                    break;
                case "pending":
                    extensions = extensionService.getPending(true);
                    break;
                case "inactive":
                    extensions = extensionService.getInactive();
                    break;
                default:
                    break;
            }
        }
        modelAndView.addObject("extensions", extensions);
        return modelAndView;
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
        redirectAttributes.addFlashAttribute("confirmMessage", "Extension " + extension.getName() + " has been approved");
        return mav;
    }

    @GetMapping(value = "adminPanel/toggle/{extensionId}")
    public ModelAndView toggleFeatured(@PathVariable long extensionId, RedirectAttributes redirectAttributes) throws NotFoundException{
        ModelAndView modelAndView = new ModelAndView("redirect:/adminPanel");
        Extension extension = extensionService.toggleFeaturedExtension(extensionId);
        redirectAttributes.addFlashAttribute(extension);

        return modelAndView;
    }

    @GetMapping(value = "adminPanel/pending")
    public ModelAndView getPending(RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("redirect:/adminPanel");
        redirectAttributes.addFlashAttribute("choice","pending");
        return modelAndView;
    }

    @GetMapping(value = "adminPanel/featured")
    public ModelAndView getFeatured(){
        ModelAndView modelAndView = new ModelAndView("redirect:/adminPanel");
        modelAndView.addObject("choice","featured");
        return modelAndView;
    }

    @GetMapping(value = "adminPanel/all")
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView("redirect:/adminPanel");
        modelAndView.addObject("choice","all");
        return modelAndView;
    }

    @GetMapping(value = "adminPanel/inactive")
    public ModelAndView getInactive(){
        ModelAndView modelAndView = new ModelAndView("redirect:/adminPanel");
        modelAndView.addObject("choice","inactive");
        return modelAndView;
    }



}

