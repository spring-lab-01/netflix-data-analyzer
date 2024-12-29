package com.hk.prj.netflix_data_analyzer.controller;

import com.hk.prj.netflix_data_analyzer.AnalysisService;
import com.hk.prj.netflix_data_analyzer.service.UserService;
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
    private final UserService userService;

    public FileUploadController(AnalysisService fileUploadService, UserService userService) {
        this.analysisService = fileUploadService;
        this.userService = userService;
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView("index") ;
        String message = "You are logged out successfully" ;
        modelAndView.getModel().put("message", message);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getHome(){
        ModelAndView modelAndView;
        String userName = userService.getLoggedInUsername();
        if( userName != null && !userName.equalsIgnoreCase("anonymousUser")) {
            modelAndView = new ModelAndView("index");
            modelAndView.getModel().put("files", analysisService.getFiles());
            modelAndView.getModel().put("accountDetail", analysisService.getAccountDetail());
            modelAndView.getModel().put("devices", analysisService.getDevices());
            //modelAndView.getModel().put("profiles", analysisService.getWatchedContent().keySet());
            modelAndView.getModel().put("contents", analysisService.getWatchedContent());
            modelAndView.getModel().put("paymentDetails", analysisService.getPaymentDetails());
        }
        else
            modelAndView = new ModelAndView("home");
        return modelAndView;
    }

    @GetMapping("uploadView")
    public String getUploadView() {
        return "upload" ;
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


