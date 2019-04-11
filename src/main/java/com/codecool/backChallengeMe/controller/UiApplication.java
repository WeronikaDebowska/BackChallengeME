package com.codecool.backChallengeMe.controller;


import java.security.Principal;

import com.codecool.backChallengeMe.services.MyUserDetailsService;
import com.codecool.backChallengeMe.model.MyUserPrincipal;
import com.codecool.backChallengeMe.services.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//@CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,
            allowedHeaders={"x-auth-token", "x-requested-with", "x-xsrf-token"}, allowCredentials = "true")
    @RequestMapping("/authorization")
    public ResponseEntity authorize() {
//        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).build();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Allow-Origin", "*");
//        headers.add("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token, X-Requested-With");
//        headers.add("Access-Control-Allow-Credentials", "true");
//
//        return new ResponseEntity<>(headers, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage() {
        return "logoutSuccessfulPage";
    }


    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,
            allowedHeaders={"x-auth-token", "x-requested-with", "x-xsrf-token"}, allowCredentials = "true")
    @GetMapping("/user")
    public String user(Principal principal) {
        log.info("/user");
        return ((MyUserPrincipal) ((Authentication) principal).getPrincipal()).getUsername();
    }
}


