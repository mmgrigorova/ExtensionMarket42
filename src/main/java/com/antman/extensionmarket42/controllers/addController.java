package com.antman.extensionmarket42.controllers;

import com.antman.extensionmarket42.models.Extension;
import com.antman.extensionmarket42.services.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
public class addController {
    private ExtensionService extensionService;

    @Autowired
    public addController(ExtensionService extensionService){
        this.extensionService = extensionService;
    }
    @RequestMapping("/add")
    public String addExtension(){
        Date date = new Date(1987,4,12);

        Extension ex = new Extension("name","descr","ver",1,"link","repolink",0,1,date,1,true,"casd");
        extensionService.save(ex);
        return "add extension";
    }
}
