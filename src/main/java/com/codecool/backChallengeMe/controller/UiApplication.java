package com.codecool.backChallengeMe.controller;


import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.responses.*;
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
import java.util.Optional;

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

    @GetMapping("/user")    //TODO should return user profile
    public String user(Principal principal) {
        return responseService.getStringUserId((Authentication) principal);
    }

    @GetMapping("/users/{user_id}/challenges")
    public ResponseEntity<List<ChallengeUserDetails>> getUserChallenges(@PathVariable("user_id") Long userId) {
        Optional<User> user = responseService.getUserById(userId);
        if (user.isPresent()) {
            List<ChallengeUserDetails> allChallengesDetails = responseService.getAllChallengeUserDetailsResponse(user.get());
            return new ResponseEntity<>(allChallengesDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/challenges/{chall_id}")
    public ResponseEntity<ChallengeDetails> getChallengeDetails(@PathVariable("chall_id") Long challId) {
        Optional<Challenge> challenge = responseService.getChallengeById(challId);
        if (challenge.isPresent()) {
            ChallengeDetails challengeDetails = responseService.createChallengeBasicResponse(challenge.get());
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("challenges/{chall_id}/participants")
    public ResponseEntity<ChallengeDetails> getChallengeParticipants(@PathVariable("chall_id") Long challId) {

        Optional<Challenge> challenge = responseService.getChallengeById(challId);
        if (challenge.isPresent()) {
            ChallengeDetails challengeDetails = responseService.createChallengeParticipantsResponse(challenge.get());
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("challenges/{chall_id}/exercises")
    public ResponseEntity<ChallengeDetails> getChallengeExercises(@PathVariable("chall_id") Long challId) {
        Optional<Challenge> challenge = responseService.getChallengeById(challId);
        if (challenge.isPresent()) {
            ChallengeDetails challengeDetails = responseService.createChallengeExerciseListResponse(challenge.get());
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("users/{user_id}/challenges/{chall_id}/executions")
    public ResponseEntity<List<ExecutionDetails>> getExecutions(@PathVariable("user_id") Long userId, @PathVariable("chall_id") Long challId) {
        Optional<Challenge> challenge = responseService.getChallengeById(challId);
        Optional<User> user = responseService.getUserById(userId);
        if (challenge.isPresent() && user.isPresent()) {
            List<ExecutionDetails> executionDetailsList = responseService.getExecutionDetails(challenge.get(), user.get());
            return new ResponseEntity<>(executionDetailsList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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