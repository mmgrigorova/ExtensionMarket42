package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("search-results")
public class SearchAndFilterExtensionsController {
    private final ExtensionService extensionService;

    @Autowired
    public SearchAndFilterExtensionsController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }


    @GetMapping
    public String displaySearchResultsByName(@RequestParam(required = false) Optional<String> name,
                                             @RequestParam(required = false) Optional<String> tagName,
                                             Model model) {
        List<Extension> matchingByCriteria = null;
        if (name.isPresent()) {
            matchingByCriteria = extensionService.getByName(name.get());
            model.addAttribute("criteria", name.get());

        }

        if (tagName.isPresent()){
            matchingByCriteria = extensionService.getByTag(tagName.get());
            model.addAttribute("criteria", tagName.get());
        }
        model.addAttribute("extensions", matchingByCriteria);
        model.addAttribute("resultCount", matchingByCriteria.size());
        return "search-results";
    }
}

