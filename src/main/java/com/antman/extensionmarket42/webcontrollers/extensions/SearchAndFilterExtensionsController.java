package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("search-results")
public class SearchAndFilterExtensionsController {
    private final ExtensionService extensionService;
    private final int PAGE_SIZE = 4;

    @Autowired
    public SearchAndFilterExtensionsController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }


    @GetMapping
    public String displaySearchResultsByName(@RequestParam(required = false) Optional<String> name,
                                             @RequestParam(required = false) Optional<String> tagName,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(name = "sortby") String sortBy,
                                             Model model) {
        Page<Extension> matchingByCriteria = null;
        if (name.isPresent()) {
            matchingByCriteria = extensionService.findAllByName(name.get(),PageRequest.of(page,PAGE_SIZE, Direction.ASC, sortBy));
            model.addAttribute("criteria", name.get());
            model.addAttribute("searchParam",name.get());
            model.addAttribute("sortby", sortBy);
        }

        if (tagName.isPresent()){
            matchingByCriteria = extensionService.findAllByTag(tagName.get(), PageRequest.of(page,PAGE_SIZE,  Sort.by(sortBy)));
            model.addAttribute("criteria", tagName.get());
            model.addAttribute("searchParam", tagName.get());
            model.addAttribute("sortby", sortBy);
        }
        model.addAttribute("extensions", matchingByCriteria);
        model.addAttribute("resultCount", matchingByCriteria.getTotalPages());

        return "search-results";
    }
}

