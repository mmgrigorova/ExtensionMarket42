package com.antman.extensionmarket42.webcontrollers.users.developers;


import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.services.users.base.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeveloperSpaceController {
    private MyUserDetailsService userDetailsService;

    @Autowired
    public DeveloperSpaceController(MyUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/developer")
    public ModelAndView showDeveloperSpace(){
        ModelAndView modelAndView = new ModelAndView("devspace");
        UserProfile userProfile =  userDetailsService.getCurrentUser();

        modelAndView.addObject("title", "Developer Space");
        modelAndView.addObject("userProfile",userProfile);

        return modelAndView;
    }
}
