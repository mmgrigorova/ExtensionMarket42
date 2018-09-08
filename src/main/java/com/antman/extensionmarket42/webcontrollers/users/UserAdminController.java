package com.antman.extensionmarket42.webcontrollers.users;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.services.users.base.UserAdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("users")
public class UserAdminController {
    private UserAdministrationService userAdministrationService;

    @Autowired
    public UserAdminController(UserAdministrationService userAdministrationService){
        this.userAdministrationService = userAdministrationService;
    }

    @GetMapping("/active")
    public ModelAndView getAllActiveUsers(){
        Iterable<User> usersActive = userAdministrationService.getAllActiveUsers();
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users",usersActive);
        return mav;
    }

    @GetMapping("/inactive")
    public ModelAndView getAllInactiveUsers(){
        Iterable<User> usersInactive = userAdministrationService.getAllInactiveUsers();
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users",usersInactive);
        return mav;
    }

    @GetMapping("/deactivate/{username}")
    public ModelAndView deactivateUser(@PathVariable String username, RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView("redirect:/users/active");
        User deactivatedUser = userAdministrationService.deactivateUser(username);
        String deactivatedUsernameString = deactivatedUser.getUsername();
        redirectAttributes.addFlashAttribute("usernameDeactivated", deactivatedUsernameString);
        redirectAttributes.addFlashAttribute("deactivatedMessage", "User " + deactivatedUsernameString + " has been deactivated");
        return mav;
    }
}
