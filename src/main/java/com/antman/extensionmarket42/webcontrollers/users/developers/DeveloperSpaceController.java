package com.antman.extensionmarket42.webcontrollers.users.developers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeveloperSpaceController {
    @GetMapping("/developer")
    public String showDeveloperSpace(Model model) {
        model.addAttribute("title", "Developer Space");
        return "devspace";
    }
}
