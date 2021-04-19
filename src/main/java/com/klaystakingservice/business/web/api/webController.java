package com.klaystakingservice.business.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
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

    @GetMapping("/signUp")
    public String register(){
        return "login/signUp";
    }

    @GetMapping("/main")
    public String main(){
        return "login/main";
    }


}
