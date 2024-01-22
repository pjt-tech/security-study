package com.mac.test.controller;

import com.mac.test.entity.User;
import com.mac.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;


@Controller
public class SecurityController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;


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
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        user.setCreateDate(LocalDateTime.now());

        String password = user.getPassword();
        String encodePwd = encoder.encode(password);
        user.setPassword(encodePwd);

        userRepository.save(user);

        return "redirect:/loginForm";
    }



    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }


    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료됨";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {

        return "info";
    }
}
