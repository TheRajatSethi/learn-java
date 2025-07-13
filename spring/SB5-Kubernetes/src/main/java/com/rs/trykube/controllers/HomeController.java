package com.rs.trykube.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${website_name}")
    String website;

    @GetMapping("/")
    String home(){
        return "Welcome to %s".formatted(website);
    }
}
