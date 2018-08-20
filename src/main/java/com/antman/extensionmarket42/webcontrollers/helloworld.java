package com.antman.extensionmarket42.webcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloworld {
    @GetMapping("/helloworld")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("/developer")
    public String showDeveloperSpace(){
        return "Hello World";
    }
}
