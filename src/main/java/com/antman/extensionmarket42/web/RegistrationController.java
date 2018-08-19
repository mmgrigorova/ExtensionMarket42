package com.antman.extensionmarket42.web;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserDto;
import com.antman.extensionmarket42.services.UserServices.base.UserRegistrationService;
import com.antman.extensionmarket42.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView registerUser(@ModelAttribute("user") @Valid UserDto userDto,
                               WebRequest request,
                               BindingResult bindingResult,
                               Model model){
        User registered = new User();

        if(!bindingResult.hasErrors()){
            registered = createUserAccount(userDto, bindingResult);
        }

        if (registered == null){
            return new ModelAndView("register", "userDto", userDto);
        } else {
            return new ModelAndView("register-confirmation", "userDto", userDto);
        }
    }

    private User createUserAccount(UserDto userDto, BindingResult bindingResult){
        User registered = null;
        try{
            registered = userRegistrationService.registerNewUserAccount(userDto);
        } catch (EmailExistsException e){
            return null;
        }

        return registered;
    }
}
