package com.antman.extensionmarket42.webcontrollers.users.developers;


import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeveloperSpaceController {
    private MyUserDetailsService userDetailsService;
    private ExtensionService extensionService;

    @Autowired
    public DeveloperSpaceController(MyUserDetailsService userDetailsService, ExtensionService extensionService){
        this.userDetailsService = userDetailsService;
        this.extensionService = extensionService;
    }

    @GetMapping("/developer")
    public ModelAndView showDeveloperSpace(){
        ModelAndView modelAndView = new ModelAndView("devspace");
        UserProfile userProfile =  userDetailsService.getCurrentUser();

        List<Extension> extensions = extensionService.getByUserId(userProfile.getUserId());
        modelAndView.addObject("title", "Developer Space");
        modelAndView.addObject("userProfile",userProfile);
        modelAndView.addObject("extensions",extensions);

        return modelAndView;
    }

    @RequestMapping(value = "/developer/edit/{extensionId}", method=RequestMethod.GET)
    public ModelAndView editExtension(@PathVariable("extensionId") long extensionId) throws Exception{
        ModelAndView modelAndView = new ModelAndView("editExtension");

        Extension extension = extensionService.getById(extensionId);
        modelAndView.addObject("extension",extension);
        return modelAndView;
    }

}
