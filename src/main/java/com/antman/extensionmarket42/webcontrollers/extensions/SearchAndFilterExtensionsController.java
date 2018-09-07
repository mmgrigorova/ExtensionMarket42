package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/downloadCount")
    public String displayResultsByDownloadCount(Model model){
        List<Extension> sortedExtensions = extensionService.orderByDownloadsCount();
        model.addAttribute("extensions",sortedExtensions);

        return "/search-results";
    }
    @GetMapping("/lastCommit")
    public String displayResultsByLastCommit(Model model){
        List<Extension> sortedExtensions = extensionService.orderByLastCommit();
        model.addAttribute("extensions",sortedExtensions);

        return "/search-results";
    }

    @GetMapping("/uploadDate")
    public String displayResultsByUploadDate(Model model){
        List<Extension> sortedExtensions = extensionService.orderByUploadDate();
        model.addAttribute("extensions",sortedExtensions);

        return "/search-results";
    }
    @GetMapping("/name")
    public ModelAndView displayResultsByName(@RequestParam(defaultValue = "0") int page){
        Page<Extension> sortedExtensions = extensionService.findAll(new PageRequest(page,5));
        ModelAndView modelAndView = new ModelAndView("/search-results");
        modelAndView.addObject("extensions",sortedExtensions);

        return modelAndView;
    }
}

