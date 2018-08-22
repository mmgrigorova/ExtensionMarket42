package com.antman.extensionmarket42.webcontrollers.users;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.services.users.base.UserDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DisplayUsersController {
    private UserDisplayService userDisplayService;

    @Autowired
    public DisplayUsersController(UserDisplayService userDisplayService){
        this.userDisplayService = userDisplayService;
    }

    @RequestMapping("users")
    public ModelAndView getAllUsers(Model model){
        Iterable<User> users = userDisplayService.getAll();
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users",users);

        return mav;
    }



}
