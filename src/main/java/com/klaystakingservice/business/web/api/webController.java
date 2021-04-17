package com.klaystakingservice.business.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webController {

    @GetMapping("/header")
    public String header(){
        return "form/header";
    }
    @GetMapping("/headerMeta")
    public String headerMeta(){
        return "form/headerMeta";
    }
    @GetMapping("/")
    public String login(){
        return "login/login";
    }
    @GetMapping("/register")
    public String register(){
        return "login/register";
    }
}
