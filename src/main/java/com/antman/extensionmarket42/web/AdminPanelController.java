package com.antman.extensionmarket42.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {
    @GetMapping("/AdminPanel")
    public String showAdminPanel(){
        return "AdminPanel";
    }

}
