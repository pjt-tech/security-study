package com.mac.test.controller;

import com.mac.test.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SecurityController {


    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }


    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }


    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }


    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }


    @GetMapping("/joinForm")
    public @ResponseBody String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public @ResponseBody String join(@RequestBody User user) {
        System.out.println("user = " + user);
        return "join";
    }



    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }


    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료됨";
    }
}
