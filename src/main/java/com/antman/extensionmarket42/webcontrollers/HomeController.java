package com.antman.extensionmarket42.webcontrollers;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class HomeController {
    private final ExtensionService extensionService;

    @Autowired
    public HomeController(ExtensionService extensionService) {
        this.extensionService = extensionService;
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

        if (authentication != null){
            userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("User has authorities: " + userDetails.getAuthorities());
        }

        return mav;
    }
}
