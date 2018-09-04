package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("search-results")
public class SearchAndFilterExtensionsController {
    private final ExtensionService extensionService;

    @Autowired
    public SearchAndFilterExtensionsController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }


    @GetMapping
    public String displaySearchResultsByName(@RequestParam String name,
                                       Model model){
        List<Extension> matchingByName = extensionService.getByName(name);
        model.addAttribute("name", name);
        model.addAttribute("extensions", matchingByName);
        model.addAttribute("resultCount", matchingByName.size());
        return "search-results";
    }

    @GetMapping
    public String displaySearchResultsByTag(@RequestParam String tagname,
                                       Model model){
        List<Extension> matchingByTag = extensionService.getByTag(tagname);
        model.addAttribute("tagname", tagname);
        model.addAttribute("extensions", matchingByTag);
        model.addAttribute("resultCount", matchingByTag.size());
        return "search-results";
    }

}

