package com.antman.extensionmarket42.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView showHome(Authentication authentication){
        ModelAndView mav = new ModelAndView("index");
        UserDetails userDetails;

        if (authentication != null){
            userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("User has authorities: " + userDetails.getAuthorities());
            return mav.addObject("username", userDetails.getUsername());
        }

        return mav;
    }
}
