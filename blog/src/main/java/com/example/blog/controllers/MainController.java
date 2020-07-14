package com.example.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model, @RequestParam(name="title", required=false, defaultValue="World") String title) {
        model.addAttribute("title", title);
        return "main/home";
    }

    @GetMapping("/about")
    public String about(Model model, @RequestParam(name="title", required=false, defaultValue="About") String title) {
        model.addAttribute("title", title);
        return "about/about";
    }
}
