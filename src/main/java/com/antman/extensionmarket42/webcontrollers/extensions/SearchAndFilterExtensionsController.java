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
        Page<Extension> matchingByCriteria = null;
        if (name.isPresent()) {
            matchingByCriteria = extensionService.findAllByName(name.get(),PageRequest.of(0,5));
            model.addAttribute("criteria", name.get());

        }

        if (tagName.isPresent()){
            matchingByCriteria = extensionService.findAllByTag(tagName.get(), PageRequest.of(0,5));
            model.addAttribute("criteria", tagName.get());
        }
        model.addAttribute("extensions", matchingByCriteria);

        System.out.println(matchingByCriteria.getTotalPages());
        model.addAttribute("resultCount", matchingByCriteria.getTotalPages());

        return "search-results";
    }

    @GetMapping("/uploadDate")
    public ModelAndView displayResultsByAddedOn(@RequestParam(defaultValue = "0") int page){
        Page<Extension> sortedExtensions = extensionService.findAllByAddedOn(PageRequest.of(page,5));
        ModelAndView modelAndView = new ModelAndView("/search-results");

        modelAndView.addObject("extensions",sortedExtensions);

        return modelAndView;
    }
    @GetMapping("/lastCommit")
    public ModelAndView displayResultsByLastCommit(@RequestParam(defaultValue = "0") int page){
        Page<Extension> sortedExtensions = extensionService.findAllByCommit(PageRequest.of(page,5));
        ModelAndView modelAndView = new ModelAndView("/search-results");

        modelAndView.addObject("extensions",sortedExtensions);

        return modelAndView;
    }

    @GetMapping("/downloadCount")
    public ModelAndView displayResultsByUploadDate(@RequestParam(defaultValue = "0") int page){
        Page<Extension> sortedExtensions = extensionService.findAllByDownloads(PageRequest.of(page,5));
        ModelAndView modelAndView = new ModelAndView("/search-results");

        modelAndView.addObject("extensions",sortedExtensions);

        return modelAndView;
    }
    @GetMapping("/name")
    public ModelAndView displayResultsByName(@RequestParam(defaultValue = "0") int page){
        Page<Extension> sortedExtensions = extensionService.findAll(PageRequest.of(page,5));
        ModelAndView modelAndView = new ModelAndView("/search-results");
        modelAndView.addObject("extensions",sortedExtensions);

        return modelAndView;
    }
}

