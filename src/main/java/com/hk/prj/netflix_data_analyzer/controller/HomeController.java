package com.hk.prj.netflix_data_analyzer.controller;

import com.hk.prj.netflix_data_analyzer.service.AnalysisService;
import com.hk.prj.netflix_data_analyzer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final AnalysisService analysisService;
    private final UserService userService;

    public HomeController(AnalysisService fileUploadService, UserService userService) {
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
    public String getHome(Model model) {
        String userName = userService.getLoggedInUsername();
        if( userName != null && !userName.equalsIgnoreCase("anonymousUser")) {
            model.addAttribute("files", analysisService.getFiles());
            model.addAttribute("accountDetail", analysisService.getAccountDetail());
            model.addAttribute("devices", analysisService.getDevices());
            model.addAttribute("contents", analysisService.getWatchedContent());
            model.addAttribute("paymentDetails", analysisService.getPaymentDetails());
            return "index";
        }
        else
            return "home";
    }

}

