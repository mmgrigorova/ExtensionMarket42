package com.antman.extensionmarket42.webcontrollers;

import com.antman.extensionmarket42.restcontrollers.FileController;
import com.antman.extensionmarket42.services.files.base.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class FileWebController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final String PROBLEM_MESSAGE = "There was a problem with the file upload. Please try again later.";

    private FileStorageService fileStorageService;

    @Autowired
    public FileWebController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/uploadFile/{id}")
    public String showUploadFileForm(Model model) {
        return "extension-upload";
    }

    @PostMapping("/uploadFile/{id}")
    public ModelAndView uploadFile(@PathVariable("id") Long id,
                                   @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return new ModelAndView("extension-upload");
        }

        String fileName = fileStorageService.storeFile(file, id);

        if (fileName == null) {
            ModelAndView mav = new ModelAndView("extension-upload");
            mav.addObject("message", PROBLEM_MESSAGE);
            return mav;
        }

        ModelAndView mav = new ModelAndView("extension-upload");

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        mav.addObject("fileDownloadUri", fileDownloadUri);
        mav.addObject("message", "File " + fileName + " has been uploaded successfully");
        return mav;
    }
}
