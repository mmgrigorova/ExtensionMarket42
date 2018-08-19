package com.antman.extensionmarket42.web;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserDto;
import com.antman.extensionmarket42.services.UserServices.base.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private UserRegistrationService userRegistrationService;

    @Autowired
    public RegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }


    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new UserDto());

        return "register";
    }

    @PostMapping("registerUser")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("user", new UserDto());
            model.addAttribute("registrationError", "Username/password cannot be empty.");
            return "register";
        }

        if(userRegistrationService.checkUserExist(user.getUsername())){
            model.addAttribute("user", new UserDto());
            model.addAttribute("user", "This username is already taken.");
            return "register";
        }

       userRegistrationService.createUser(user);

        return "register-confirmation";
    }
}
