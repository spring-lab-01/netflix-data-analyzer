package com.hk.prj.netflix_data_analyzer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {

    private final FileUploadService fileUploadService;

    public HomeController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @GetMapping
    public String getHome(){
        return "index";
    }

    @GetMapping("/upload")
    public String getUpload(){
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            fileUploadService.upload(file);
            model.addAttribute("file", file.getOriginalFilename());
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "upload";
    }

    @GetMapping("/devices")
    public String getDevices(Model model) {
        String message = "";
        try {
            model.addAttribute("devices", fileUploadService.getDevices());
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not get Devices. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "devices";
    }

    @GetMapping("/streaming-devices")
    public String getStreamingDevices(Model model) {
        String message = "";
        try {
            model.addAttribute("streamingDevices", fileUploadService.getIpAddressStreaming());
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not get Streaming Devices. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "streaming-devices";
    }

    @GetMapping("/watched-content")
    public String getWatchedContent(Model model) {
        String message = "";
        try {
            model.addAttribute("content", fileUploadService.getWatchedContent());
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not get Watched Content. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "watched-content";
    }

    @GetMapping("/files")
    public String getFiles(Model model) {
        String message = "";
        try {
            model.addAttribute("files", fileUploadService.getFiles());
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not get Devices. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "files";
    }


}


