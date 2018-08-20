package com.antman.extensionmarket42.web;

import com.antman.extensionmarket42.extensions.models.Extension;
import com.antman.extensionmarket42.extensions.services.base.ExtensionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminPanelController {
    private ExtensionService extensionService;

    public AdminPanelController(ExtensionService extensionService){
        this.extensionService=extensionService;
    }

    @GetMapping("/AdminPanel")
    public String showAdminPanel(Model model){
        return "adminPanel";
    }

    @PostMapping("/AdminPanel")
    public String searchSubmit(Model model){
        Iterable<Extension> extensions = extensionService.getAll();

        for(Extension e:extensions){
            System.out.print(e.getId());
        }
        model.addAttribute("extensions",extensions);
        return "search";
    }

}
