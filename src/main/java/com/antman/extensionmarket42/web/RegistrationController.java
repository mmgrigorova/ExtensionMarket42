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
import org.springframework.web.context.request.WebRequest;

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
        model.addAttribute("userDto", new UserDto());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserDto userDto,
                               WebRequest request,
                               BindingResult bindingResult,
                               Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("userDto", new UserDto());
            model.addAttribute("registrationError", "Username/password cannot be empty.");
            return "register";
        }

        if(userRegistrationService.checkUserExist(userDto.getEmail())){
            model.addAttribute("userDto", new UserDto());
            model.addAttribute("error", "This username is already taken.");
            return "register";
        }

//       userRegistrationService.createUser(userDto);

        return "register-confirmation";
    }
}
