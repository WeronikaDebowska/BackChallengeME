package com.codecool.backChallengeMe.controller;


import java.security.Principal;

import com.codecool.backChallengeMe.services.MyUserDetailsService;
import com.codecool.backChallengeMe.model.MyUserPrincipal;
import com.codecool.backChallengeMe.services.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class UiApplication {

    private MyUserDetailsService myUserDetailsService;


    @Autowired
    public UiApplication(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }


    @GetMapping("/loginpage")
    public ResponseEntity loginpage() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @GetMapping("/login")
    public ResponseEntity login() {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage() {
        return "logoutSuccessfulPage";
    }


    @GetMapping("/user")
    public String user(Principal principal) {
        log.info("/user");
        String username = ((MyUserPrincipal) ((Authentication) principal).getPrincipal()).getUsername();
        return username;

    }
}
