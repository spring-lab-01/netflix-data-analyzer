package com.hk.prj.netflix_data_analyzer.controller;

import com.hk.prj.netflix_data_analyzer.AnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DevicesController {

    private final AnalysisService analysisService;

    public DevicesController(AnalysisService fileUploadService) {
        this.analysisService = fileUploadService;
    }

    @GetMapping("/devices")
    public String getDevices(Model model) {
        String message = "";
        try {
            model.addAttribute("devices", analysisService.getDevices());
            model.addAttribute("streamingDevices", analysisService.getIpAddressStreaming());
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not get Devices. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "devices";
    }

}


