package com.antman.extensionmarket42.webcontrollers.extensions;

import com.antman.extensionmarket42.dtos.ExtensionDto;
import com.antman.extensionmarket42.models.extensions.Extension;
import com.antman.extensionmarket42.services.extensions.ExtensionService;
import com.antman.extensionmarket42.services.files.FileStorageService;
import com.antman.extensionmarket42.services.users.base.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Date;


@Controller
public class AddExtensionController {
    private ExtensionService extensionService;
    private FileStorageService fileStorageService;
    private final String PROBLEM_MESSAGE = "There was a problem with the file upload. Please try again later.";

    @Autowired
    public AddExtensionController(ExtensionService extensionService, FileStorageService fileStorageService) {
        this.extensionService = extensionService;
        this.fileStorageService = fileStorageService;
    }


    @GetMapping("extension-add")
    public ModelAndView showAddExtension() {
        ModelAndView mav = new ModelAndView("extension-add");
        mav.addObject("extensionDto", new ExtensionDto());
        return mav;
    }

    @PostMapping("extension-add")
    public ModelAndView addExtension(@ModelAttribute("extensionDto") ExtensionDto extensionDto,
                                     @RequestParam("file") MultipartFile file,
                                     RedirectAttributes redirectAttributes,
                                     Errors errors) throws Exception {


        if (errors.hasErrors()) {
            ModelAndView mav = new ModelAndView("extension-add");
            mav.addObject("errors", errors);
            return mav;
        }

        try {
            ModelAndView mav = new ModelAndView("redirect:/extension-details/{id}");
            extensionDto.setFileName(file.getOriginalFilename());
            Extension newExtension = extensionService.save(extensionDto);

            redirectAttributes.addAttribute("id", newExtension.getId());

            if (file.isEmpty()) {
                redirectAttributes.addAttribute("message", "Please select a file to upload");
                return new ModelAndView("extension-upload");
            }

            String fileName = fileStorageService.storeFile(file);

            if (fileName == null) {
                ModelAndView mavError = new ModelAndView("extension-upload");
                mavError.addObject("message", PROBLEM_MESSAGE);
                return mavError;
            }

            redirectAttributes.addFlashAttribute("messageCreated", "Extension created!");
            redirectAttributes.addFlashAttribute("messageUploaded", "File " + fileName + " has been uploaded successfully");
            return mav;

        } catch (Exception e) {
            throw new Exception("There was a problem with creating this exception: " + e.getCause());
        }
    }
}