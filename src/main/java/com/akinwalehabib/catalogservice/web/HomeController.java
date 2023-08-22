package com.akinwalehabib.catalogservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akinwalehabib.catalogservice.config.PolarProperties;

@RestController
public class HomeController {
    private PolarProperties polarProperties;

    public HomeController(PolarProperties polarProperties) {
        this.polarProperties = polarProperties;
    }

    @GetMapping("/")
    public String getGreeting() {
        return polarProperties.getGreeting();
    }
}
