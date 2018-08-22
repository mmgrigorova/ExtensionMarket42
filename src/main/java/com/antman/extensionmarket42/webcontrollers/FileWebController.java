package com.antman.extensionmarket42.webcontrollers;

import com.antman.extensionmarket42.payload.UploadFileResponse;
import com.antman.extensionmarket42.restcontrollers.FileController;
import com.antman.extensionmarket42.services.files.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class FileWebController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private FileStorageService fileStorageService;

    @Autowired
    public FileWebController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/uploadFile")
    public String showUploadFileForm(Model model) {
        return "extension-add";
    }

    @PostMapping("/uploadFile")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return new ModelAndView("extension-add");
        }

        String fileName = fileStorageService.storeFile(file);

        if (fileName == null) {
            return new ModelAndView("extension-add");
        }

        ModelAndView mav = new ModelAndView("extension-add");

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        mav.addObject("fileDownloadUri", fileDownloadUri);
        mav.addObject("message", "File " + fileName + " has been uploaded successfully");
        return mav;
    }
}
