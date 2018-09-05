package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.services.extensions.ExtensionService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("refreshRepository")
public class RepositoryReloadController {
    private final ExtensionService extensionService;
    private final Logger logger = LoggerFactory.getLogger(RepositoryReloadController.class);
    private final String ERROR_MESSAGE = "We could not refresh the information at this moment";


    @Autowired
    public RepositoryReloadController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping("/{id}")
    public String refreshSingleExtensionRepositoryData(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            redirectAttributes.addAttribute("id", extensionService.refreshRepositoryInformationPerExtension(id).getId());
            redirectAttributes.addFlashAttribute("successmessage", "Repository data has been refreshed");
        } catch (NotFoundException | IOException e) {
            logger.error(ERROR_MESSAGE, e);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errormessage", "We could not refresh the information");
        }
        return "redirect:/extension-details/{id}";
    }

}
