package com.hk.prj.netflix_data_analyzer.controller;

import com.hk.prj.netflix_data_analyzer.AnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WatchedContentController {

    private final AnalysisService fileUploadService;

    public WatchedContentController(AnalysisService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/watched-content")
    public String getWatchedContent(Model model) {
        String message = "";
        try {
            model.addAttribute("content", fileUploadService.getWatchedContentMap());
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not get Watched Content. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "watched-content";
    }


}


