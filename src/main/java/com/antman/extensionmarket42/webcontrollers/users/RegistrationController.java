package com.antman.extensionmarket42.webcontrollers.users;

import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserDto;
import com.antman.extensionmarket42.services.users.base.UserRegistrationService;
import com.antman.extensionmarket42.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String showRegistrationPage(Model model) {
        model.addAttribute("userDto", new UserDto());

        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                                     Errors errors) {


        if (errors.hasErrors()) {
            ModelAndView mav = new ModelAndView("register");
            mav.addObject("userDto", userDto);
            mav.addObject(errors);
            return mav;
        }

        User registeredUser = createUserAccount(userDto);

        if (registeredUser == null) {
            return new ModelAndView("register", "userDto", userDto);
        }

        return new ModelAndView("register-confirmation", "userDto", userDto);
    }

    private User createUserAccount(UserDto userDto) {
        User registered = null;
        try {
            registered = userRegistrationService.registerNewUserAccount(userDto);
        } catch (EmailExistsException e) {
            return null;
        }

        return registered;
    }
}
