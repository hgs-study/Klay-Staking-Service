package com.klaystakingservice.business.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@Slf4j
public class webController {

    @GetMapping("/header")
    public String header(){
        return "form/header";
    }

    @GetMapping("/footer")
    public String footer(){
        return "form/footer";
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
    public String main(Principal principal,Model model){
        model.addAttribute("userEmail",principal.getName());
        return "login/main";
    }



}
