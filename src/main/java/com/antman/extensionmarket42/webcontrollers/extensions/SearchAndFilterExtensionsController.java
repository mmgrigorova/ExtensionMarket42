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

@Controller
@RequestMapping("search-results")
public class SearchAndFilterExtensionsController {
    private final ExtensionService extensionService;
    private final int PAGE_SIZE = 10;

    @Autowired
    public SearchAndFilterExtensionsController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }


    @GetMapping
    public String displaySearchResultsByName(@RequestParam(name = "searchby") String searchBy,
                                             @RequestParam String value,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(name = "sortby") String sortBy,
                                             Model model) {
        Page<Extension> matchingByCriteria = null;

        if (searchBy.equals("name")) {
            matchingByCriteria = extensionService.findAllByName(value,PageRequest.of(page,PAGE_SIZE, Direction.ASC, sortBy));
        }

        if (searchBy.equals("tagname")){
            matchingByCriteria = extensionService.findAllByTag(value, PageRequest.of(page,PAGE_SIZE, Direction.ASC, sortBy));
        }
        model.addAttribute("searchValue", value);
        model.addAttribute("searchParam",searchBy);
        model.addAttribute("sortby", sortBy);
        model.addAttribute("extensions", matchingByCriteria);
        model.addAttribute("resultCount", matchingByCriteria.getTotalPages());
        model.addAttribute("totalResultCount", matchingByCriteria.getTotalElements());

        return "search-results";
    }
}

