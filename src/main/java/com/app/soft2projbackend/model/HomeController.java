package com.app.soft2projbackend.model;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
}
