package com.codecool.backChallengeMe.controller;


import com.codecool.backChallengeMe.DAO.ChallengeRepository;
import com.codecool.backChallengeMe.DAO.UserRepository;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,
        allowedHeaders = {"*"},
        allowCredentials = "true")
@RestController
@Slf4j
public class UiApplication {


    private ResponseService responseService;
    private UserRepository userRepository;
    private ChallengeRepository challengeRepository;

    @Autowired
    public UiApplication(ResponseService responseService, UserRepository userRepository, ChallengeRepository challengeRepository) {
        this.responseService = responseService;
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
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
        Optional<User> optUser = userRepository.findById(userId);


//        return optUser.map(user -> new ResponseEntity<>(responseService.getAllChallengeUserDetailsResponse(user), HttpStatus.OK))
//                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


        if (optUser.isPresent()) {
            List<ChallengeUserDetails> allChallengesDetails = responseService.getAllChallengeUserDetailsResponse(optUser.get());
            return new ResponseEntity<>(allChallengesDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/challenges/{chall_id}")
    public ResponseEntity<ChallengeDetails> getChallengeDetails(@PathVariable("chall_id") Long challId) {
        Optional<Challenge> optChallenge = challengeRepository.findById(challId);

//        return optChallenge.map(challenge -> new ResponseEntity<>(responseService.createChallengeBasicResponse(challenge), HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        if (optChallenge.isPresent()) {
            ChallengeDetails challengeDetails = responseService.createChallengeBasicResponse(optChallenge.get());
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("challenges/{chall_id}/participants")
    public ResponseEntity<List<User>> getChallengeParticipants(@PathVariable("chall_id") Long challId) {

        Optional<Challenge> challenge = challengeRepository.findById(challId);
        if (challenge.isPresent()) {
//            ChallengeDetails challengeDetails = responseService.createChallengeParticipantsResponse(challenge.get());
            return new ResponseEntity<>(responseService.createChallengeParticipantsResponse(challenge.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("challenges/{chall_id}/exercises")
    public ResponseEntity<List<Exercise>> getChallengeExercises(@PathVariable("chall_id") Long challId) {
        Optional<Challenge> challenge = challengeRepository.findById(challId);
        if (challenge.isPresent()) {
            List<Exercise> exercises = responseService.getExerciseList(challenge.get());
            return new ResponseEntity<>(exercises, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("users/{user_id}/challenges/{chall_id}/executions")
    public ResponseEntity<List<ExecutionDetails>> getExecutions(@PathVariable("user_id") Long userId, @PathVariable("chall_id") Long challId) {
        Optional<Challenge> challenge = challengeRepository.findById(challId);
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