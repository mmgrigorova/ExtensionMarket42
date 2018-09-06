package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.payload.RepositorySyncStatistics;
import com.antman.extensionmarket42.services.extensions.ExtensionRepositoryDataService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("refreshRepository")
public class RepositoryReloadController {
    private final ExtensionRepositoryDataService extensionRepoService;
    private final Logger logger = LoggerFactory.getLogger(RepositoryReloadController.class);
    private final String ERROR_MESSAGE = "We could not refresh the information at this moment";


    @Autowired
    public RepositoryReloadController(ExtensionRepositoryDataService extensionRepoService) {
        this.extensionRepoService = extensionRepoService;
    }

    @GetMapping("/{id}")
    public String refreshSingleExtensionRepositoryData(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try {
            redirectAttributes.addAttribute("id", extensionRepoService.refreshRepositoryInfoPerExtension(id).getId());
            redirectAttributes.addFlashAttribute("successmessage", "Repository data has been refreshed");
        } catch (NotFoundException | IOException e) {
            logger.error(ERROR_MESSAGE, e);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errormessage", "We could not refresh the information");
        }
        return "redirect:/extension-details/{id}";
    }

    @GetMapping("/all")
    public String refreshAllExtensionRepositoryData(RedirectAttributes redirectAttributes){
        RepositorySyncStatistics stats = extensionRepoService.refreshRepositoryInfoAllActiveExtensions();
        redirectAttributes.addFlashAttribute("successmessage",
                "GitHub repository data has been successfully refreshed for " + stats.getSuccessfulExtensions().size() + " active extensions.");
        return "redirect:/adminPanel";
    }
}
