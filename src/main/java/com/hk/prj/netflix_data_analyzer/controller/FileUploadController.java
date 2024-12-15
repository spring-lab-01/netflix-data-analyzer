package com.hk.prj.netflix_data_analyzer.controller;

import com.hk.prj.netflix_data_analyzer.AnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {

    private final AnalysisService analysisService;

    public FileUploadController(AnalysisService fileUploadService) {
        this.analysisService = fileUploadService;
    }

    @GetMapping
    public ModelAndView getHome(){
        ModelAndView modelAndView = new ModelAndView("index") ;

        modelAndView.getModel().put("files", analysisService.getFiles());
        modelAndView.getModel().put("accountDetail", analysisService.getAccountDetail());
        modelAndView.getModel().put("devices", analysisService.getDevices());
        //modelAndView.getModel().put("profiles", analysisService.getWatchedContent().keySet());
        modelAndView.getModel().put("contents", analysisService.getWatchedContent());
        modelAndView.getModel().put("paymentDetails", analysisService.getPaymentDetails());

        return modelAndView;
    }

    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            analysisService.upload(file);
            model.addAttribute("file", file.getOriginalFilename());
            model.addAttribute("files", analysisService.getFiles());
            model.addAttribute("accountDetail", analysisService.getAccountDetail());
            model.addAttribute("devices", analysisService.getDevices());
            //model.addAttribute("profiles", analysisService.getWatchedContent().keySet());
            model.addAttribute("contents", analysisService.getWatchedContent());
            model.addAttribute("paymentDetails", analysisService.getPaymentDetails());

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "index";
    }

}


