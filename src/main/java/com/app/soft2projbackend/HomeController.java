package com.app.soft2projbackend;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
}
