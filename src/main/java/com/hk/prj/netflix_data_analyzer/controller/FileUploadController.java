package com.hk.prj.netflix_data_analyzer.controller;

import com.hk.prj.netflix_data_analyzer.service.AnalysisService;
import com.hk.prj.netflix_data_analyzer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
public class FileUploadController {

    private final AnalysisService analysisService;
    private final UserService userService;

    public FileUploadController(AnalysisService fileUploadService, UserService userService) {
        this.analysisService = fileUploadService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            if(file.isEmpty()) {
                message = "File is empty";
            } else if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".zip")) {
                message = "File is not zipped";
            } else
                analysisService.upload(file);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
        }
        finally {
            model.addAttribute("message", message);
        }
        if(message.isEmpty())
            return "redirect:/";
        else
            return "upload";
    }

}


