package com.antman.extensionmarket42.webcontrollers;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.models.extensions.Tag;
import com.antman.extensionmarket42.repositories.base.TagRepository;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
public class HomeController {
    private final ExtensionService extensionService;
    private final TagRepository tagRepository;

    @Autowired
    public HomeController(ExtensionService extensionService, TagRepository tagRepository) {
        this.extensionService = extensionService;
        this.tagRepository = tagRepository;
    }

    @GetMapping("/")
    public ModelAndView showHome(Authentication authentication){
        ModelAndView mav = new ModelAndView("index");
        UserDetails userDetails;

        List<Extension> extensionFeaturedList = extensionService.getApprovedFeatured(true);
        mav.addObject("featuredExtensions", extensionFeaturedList);

        List<Extension> extensionsMostPopular = extensionService.getTopFiveMostPopularApproved();
        mav.addObject("mostPopular", extensionsMostPopular);

        List<Extension> recentlyAdded = extensionService.getRecentlyAdded();
        mav.addObject("recentlyAdded", recentlyAdded);

        List<Tag> tags = StreamSupport.stream(tagRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        mav.addObject("tags", tags);

        if (authentication != null){
            userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("User has authorities: " + userDetails.getAuthorities());
        }

        return mav;
    }
}
