package com.mac.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SecurityController {


    @GetMapping("/")
    public String index() {
        return "index";
    }
}
