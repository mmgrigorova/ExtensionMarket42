package com.antman.extensionmarket42.restcontrollers;

import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/extensions")
public class ExtensionsRestController {
    private ExtensionService extensionService;

    @Autowired
    public ExtensionsRestController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }


    @GetMapping("/featured")
    public List<Extension> getFeaturedExtensions() {
        return extensionService.getApprovedFeatured(true);
    }

    @GetMapping("/popular")
    public List<Extension> getMostPopularExtensions() {
        return extensionService.getMostPopularApproved();
    }

    @GetMapping("/recent")
    public List<Extension> getRecentExtensions() {
        return extensionService.getRecentlyAdded();
    }
}
