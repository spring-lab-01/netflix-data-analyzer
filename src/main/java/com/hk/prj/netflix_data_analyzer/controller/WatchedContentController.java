package com.hk.prj.netflix_data_analyzer.controller;

import com.hk.prj.netflix_data_analyzer.service.AnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WatchedContentController {

    private final AnalysisService analysisService;

    public WatchedContentController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/watched-content/{profile}/{year}")
    public String getWatchedContent(@PathVariable("profile") String profile, @PathVariable("year") String year, Model model) {
        String message = "";
        try {
            model.addAttribute("content", analysisService.getViewingActivityByProfileAndYear(profile, year));
            model.addAttribute("profile", profile);
            model.addAttribute("year", year);
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not get Watched Content. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "watched-content";
    }


}


