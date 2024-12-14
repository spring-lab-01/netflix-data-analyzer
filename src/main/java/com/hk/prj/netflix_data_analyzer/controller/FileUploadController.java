package com.hk.prj.netflix_data_analyzer.controller;

import com.hk.prj.netflix_data_analyzer.AnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    private final AnalysisService analysisService;

    public FileUploadController(AnalysisService fileUploadService) {
        this.analysisService = fileUploadService;
    }

    @GetMapping("/upload")
    public String getUpload(){
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            analysisService.upload(file);
            model.addAttribute("file", file.getOriginalFilename());
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "upload";
    }

    @GetMapping("/files")
    public String getFiles(Model model) {
        String message = "";
        try {
            model.addAttribute("files", analysisService.getFiles());
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not get Devices. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "files";
    }


}


