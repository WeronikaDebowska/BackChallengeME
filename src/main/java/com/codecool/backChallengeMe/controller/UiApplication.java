package com.codecool.backChallengeMe.controller;

import com.codecool.backChallengeMe.DAO.*;
import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.responses.ChallengeDetails;
import com.codecool.backChallengeMe.model.responses.ChallengeParticipants;
import com.codecool.backChallengeMe.model.responses.ChallengeUserDetails;
import com.codecool.backChallengeMe.model.responses.ExecutionDetails;
import com.codecool.backChallengeMe.services.ResponseService;
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
    private ExecutionRepository executionRepository;

    @Autowired
    private ResponseService challengeUserService;

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

    @GetMapping("/users/{id}/challenges")
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

        ChallengeDetails challengeDetails = new ChallengeDetails();     //TODO do the searchinfg in service, not here
        Optional<Challenge> challenge = challengeRepository.findById(chall_id);
        if (challenge.isPresent()) {
            challengeDetails = challengeUserService.createChallengeDetailsResponse(challenge.get());
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("challenges/{chall_id}/participants")
    public ResponseEntity<ChallengeParticipants> getChallengeParticipants(@PathVariable("chall_id") Long chall_id) {
        Optional<Challenge> challenge = challengeRepository.findById(chall_id);
        if (challenge.isPresent()) {
            Challenge challengeToDisplay = challenge.get();
            ChallengeParticipants challengeParticipants = challengeUserService.createChallengeParticipantsResponse(challengeToDisplay);

            return new ResponseEntity(challengeParticipants, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @GetMapping("challenges/{chall_id}/exercises")
    public ResponseEntity<List<Exercise>> getChallengeExercises(@PathVariable("chall_id") Long chall_id) {
        Optional<Challenge> challenge = challengeRepository.findById(chall_id);
        if (challenge.isPresent()) {
            Challenge challengeToDisplay = challenge.get();
            List<Exercise> exercisesToDisplay = new LinkedList<>();
            List<ChallengeExercise> challengeExercisesList = challengeExerciseRepository.findAllByChall(challengeToDisplay);
            for (ChallengeExercise challengeExercise : challengeExercisesList) {
                exercisesToDisplay.add(challengeExercise.getExer());
            }
            return new ResponseEntity<>(exercisesToDisplay, HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("user/{user_id}/challenges/{chall_id}/executions")
    public ResponseEntity<List<ExecutionDetails>> getExecutions(@PathVariable("user_id") Long user_id, @PathVariable("chall_id") Long chall_id) {
        Optional<Challenge> challenge = challengeRepository.findById(chall_id);
        Optional<User> user = userRepository.findById(user_id);
        if (challenge.isPresent() && user.isPresent()) {
            List<Execution> executionList = executionRepository.findExecutionsByChallengeAndUser(challenge.get(), user.get());
            List<ExecutionDetails> executionDetailsList = new LinkedList<>();
            for (Execution execution : executionList) {
                executionDetailsList.add(new ExecutionDetails(execution.getId(), execution.getRepeats(), execution.getDate(), execution.getExercise()));
            }
            return new ResponseEntity<>(executionDetailsList, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/challenges")
    public ResponseEntity addChallenge(@RequestBody String challengeName) {
        //TODO add new challenge to DB
        return new ResponseEntity(HttpStatus.OK);
    }


//    @PostMapping("/challenges/{chall_id}/exercises")
//    public ResponseEntity addExercisesToChallenge(@PathVariable(value = "chall_id") Long chall_id, @RequestBody )
//


}