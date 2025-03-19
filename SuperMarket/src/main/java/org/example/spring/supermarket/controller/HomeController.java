package org.example.spring.supermarket.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "/shop";
    }



    @GetMapping("/nice-table")
    public String table() {
        return "/quixlab-master/widgets";
    }
}


