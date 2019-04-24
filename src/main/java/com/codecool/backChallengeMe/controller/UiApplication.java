package com.codecool.backChallengeMe.controller;

import com.codecool.backChallengeMe.DAO.ChallengeExerciseRepository;
import com.codecool.backChallengeMe.DAO.ChallengeRepository;
import com.codecool.backChallengeMe.DAO.ChallengeUserRepository;
import com.codecool.backChallengeMe.DAO.UserRepository;
import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.responses.ChallengeDetails;
import com.codecool.backChallengeMe.model.responses.ChallengeUserDetails;
import com.codecool.backChallengeMe.services.ChallengeUserService;
import com.codecool.backChallengeMe.services.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,
        allowedHeaders = {"*"},
        allowCredentials = "true")
@RestController
@Slf4j
public class UiApplication {

    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeUserRepository challengeUserRepository;

    @Autowired
    private ChallengeExerciseRepository challengeExerciseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeUserService challengeUserService;

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

    @RequestMapping("/authorization")
    public ResponseEntity authorize() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage() {
        return "logoutSuccessfulPage";
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        log.info("/user");
        String username = ((MyUserPrincipal) ((Authentication) principal).getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long id = user.getId();
        return "{\"id\": \"" + id + "\"}";
    }

    @GetMapping("/user/{id}/challenges")
    public ResponseEntity<List<ChallengeUserDetails>> getUserChallenges(@PathVariable("id") Long id) {

        List<ChallengeUserDetails> allChallengesDetails = new LinkedList<>();
        try {
            challengeUserService.createChallengeUserDetailsResponse(id, allChallengesDetails);
            return new ResponseEntity<>(allChallengesDetails, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(allChallengesDetails, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/challenges/{chall_id}")
    public ResponseEntity<ChallengeDetails> getChallengeDetails(@PathVariable("chall_id") Long chall_id) {
        ChallengeDetails challengeDetails = new ChallengeDetails();
        Optional<Challenge> challenge = challengeRepository.findById(chall_id);
        if (challenge.isPresent()) {
            challengeDetails = challengeUserService.createChallengeDetailsResponse(challenge.get());
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(challengeDetails, HttpStatus.NOT_FOUND);
        }
    }



}