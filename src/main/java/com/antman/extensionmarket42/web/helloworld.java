package com.antman.extensionmarket42.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloworld {
    @GetMapping("/helloworld")
    public String helloWorld(){
        return "Hello World";
    }
}