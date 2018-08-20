package com.antman.extensionmarket42.web;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminPanelController {
    private ExtensionService extensionService;

//    public AdminPanelController(ExtensionService extensionService){
//        this.extensionService=extensionService;
//    }
//
//    @GetMapping("/adminPanel")
//    public String showAdminPanel(Model model){
//        return "adminPanel";
//    }
//
//    @PostMapping("/adminPanel")
//    public String searchSubmit(Model model){
//        Iterable<Extension> extensions = extensionService.getAll();
//
//        for(Extension e:extensions){
//            System.out.print(e.getId());
//        }
//        model.addAttribute("extensions",extensions);
//        return "adminPanel";
//    }

}
