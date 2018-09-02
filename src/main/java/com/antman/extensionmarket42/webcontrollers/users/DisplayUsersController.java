package com.antman.extensionmarket42.webcontrollers.users;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.services.users.base.UserDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("users")
public class DisplayUsersController {
    private UserDisplayService userDisplayService;

    @Autowired
    public DisplayUsersController(UserDisplayService userDisplayService){
        this.userDisplayService = userDisplayService;
    }

    @GetMapping("/active")
    public ModelAndView getAllActiveUsers(){
        Iterable<User> usersActive = userDisplayService.getAllActiveUsers();
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users",usersActive);
        return mav;
    }

    @GetMapping("/inactive")
    public ModelAndView getAllInactiveUsers(){
        Iterable<User> usersInactive = userDisplayService.getAllInactiveUsers();
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users",usersInactive);
        return mav;
    }
}
