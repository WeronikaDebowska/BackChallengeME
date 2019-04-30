package com.codecool.backChallengeMe.controller;


import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.responses.ChallengeDetails;
import com.codecool.backChallengeMe.model.responses.ChallengeParticipants;
import com.codecool.backChallengeMe.model.responses.ChallengeUserDetails;
import com.codecool.backChallengeMe.model.responses.ExecutionDetails;
import com.codecool.backChallengeMe.services.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.Timestamp;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,
        allowedHeaders = {"*"},
        allowCredentials = "true")
@RestController
@Slf4j
public class UiApplication {


    private ResponseService responseService;

    @Autowired
    public UiApplication(ResponseService challengeUserService) {
        this.responseService = challengeUserService;
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
        return responseService.getStringUserId((Authentication) principal);
    }

    @GetMapping("/users/{user_id}/challenges")
    public ResponseEntity<List<ChallengeUserDetails>> getUserChallenges(@PathVariable("user_id") Long user_id) {
        return responseService.createUserAllChallengesDetailsResponse(user_id);
    }


    @GetMapping("/challenges/{chall_id}")
    public ResponseEntity<ChallengeDetails> getChallengeDetails(@PathVariable("chall_id") Long chall_id) {
        return responseService.createChallengeDetailsResponse(chall_id);
    }


    @GetMapping("challenges/{chall_id}/participants")
    public ResponseEntity<ChallengeParticipants> getChallengeParticipants(@PathVariable("chall_id") Long chall_id) {
        return responseService.createChallengeParticipantsResponse(chall_id);
    }


    @GetMapping("challenges/{chall_id}/exercises")
    public ResponseEntity<List<Exercise>> getChallengeExercises(@PathVariable("chall_id") Long chall_id) {
        return responseService.createChallengeExerciseListResponse(chall_id);
    }


    @GetMapping("users/{user_id}/challenges/{chall_id}/executions")
    public ResponseEntity<List<ExecutionDetails>> getExecutions(@PathVariable("user_id") Long user_id, @PathVariable("chall_id") Long chall_id) {
        return responseService.createChallengeUserExecution(user_id, chall_id);
    }

    @PostMapping("/challenges")
    public ResponseEntity addChallenge(@RequestBody String challengeName, Timestamp start, Timestamp finish, Long hostUserId) {
        //TODO add new challenge to DB
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/challenges")
    public ResponseEntity removeChallenge(@RequestBody Long chall_id) {
        //TODO remove challenge from DB
        return new ResponseEntity(HttpStatus.OK);
    }

}