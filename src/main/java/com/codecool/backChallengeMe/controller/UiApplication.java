package com.codecool.backChallengeMe.controller;


import com.codecool.backChallengeMe.DAO.ChallengeRepository;
import com.codecool.backChallengeMe.DAO.ExecutionRepository;
import com.codecool.backChallengeMe.DAO.ParticipationRepository;
import com.codecool.backChallengeMe.DAO.UserRepository;
import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Execution;
import com.codecool.backChallengeMe.model.Exercise;
import com.codecool.backChallengeMe.model.User;
import com.codecool.backChallengeMe.model.junctionTables.Participation;
import com.codecool.backChallengeMe.model.responses.ParticipationDetails;
import com.codecool.backChallengeMe.services.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private ParticipationRepository participationRepository;
    private ExecutionRepository executionRepository;

    @Autowired
    public UiApplication(ResponseService responseService,
                         UserRepository userRepository,
                         ChallengeRepository challengeRepository,
                         ParticipationRepository participationRepository,
                         ExecutionRepository executionRepository) {
        this.responseService = responseService;
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.participationRepository = participationRepository;
        this.executionRepository = executionRepository;
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
    public ResponseEntity<List<ParticipationDetails>> getUserChallenges(@PathVariable("user_id") Long userId) {
        Optional<User> optUser = userRepository.findById(userId);


        if (optUser.isPresent()) {
            List<ParticipationDetails> allChallengesDetails = responseService.getAllChallengeUserDetailsResponse(optUser.get());
            return new ResponseEntity<>(allChallengesDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/challenges/{chall_id}")
    public ResponseEntity<Challenge> getChallengeDetails(@PathVariable("chall_id") Long challId) {
        Optional<Challenge> optChallenge = challengeRepository.findById(challId);

        if (optChallenge.isPresent()) {
            Challenge challengeDetails = responseService.createChallengeBasicResponse(optChallenge.get());
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("challenges/{chall_id}/participants")
    public ResponseEntity<List<ParticipationDetails>> getChallengeParticipants(@PathVariable("chall_id") Long challId) {

        Optional<Challenge> challenge = challengeRepository.findById(challId);
        if (challenge.isPresent()) {
            return new ResponseEntity<>(responseService.createParticipantsResponse(challenge.get()), HttpStatus.OK);
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
    public ResponseEntity<Map<Date, List<Execution>>> getExecutions(@PathVariable("user_id") Long userId, @PathVariable("chall_id") Long challId) {
        Optional<Challenge> challenge = challengeRepository.findById(challId);
        Optional<User> user = responseService.getUserById(userId);
        if (challenge.isPresent() && user.isPresent()) {
            Map<Date, List<Execution>> executionDetailsMap = responseService.getExecutionDetails(challenge.get(), user.get());
            return new ResponseEntity<>(executionDetailsMap, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/users/{user_id}/challenges")
    public ResponseEntity addChallenge(@PathVariable("user_id") Long hostId, @RequestBody Map<String, String> params) {

        System.out.println(params.size());
        System.out.println(params.get("name") + params.get("start") + params.get("finish"));

        String challengeName = params.get("name");
        Date start = null;
        Date finish = null;
        Optional<User> host = userRepository.findById(hostId);

        Challenge challenge;

        try {
            start = new SimpleDateFormat("yyyy-MM-dd").parse(params.get("start"));
            finish = new SimpleDateFormat("yyyy-MM-dd").parse(params.get("finish"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean allParamsGiven = challengeName != null && start != null && finish != null;

        if (allParamsGiven) {
            challenge = new Challenge(challengeName, start, finish);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (host.isPresent()) {
            Participation participation = new Participation(challenge, host.get(), Participation.ChallengeStatus.accepted, Participation.ChallengeRole.host);
            try {
                challengeRepository.save(challenge);
                participationRepository.save(participation);
            } catch (DataAccessException | ConstraintViolationException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/users/{user_id}/challenges/{chall_id}/executions")
    public ResponseEntity addExecution(@PathVariable("user_id") Long userId, @PathVariable("chall_id") Long challId, @RequestBody Map<String, String> params) {
        System.out.println("saving exec");
        Long exerId = null;
        Date date = null;
        Integer repeats = null;


        try {
            exerId = Long.valueOf(params.get("id"));
            date = new SimpleDateFormat("yyyy-MM-dd").parse(params.get("date"));
            repeats = Integer.valueOf(params.get("repeats"));
            System.out.println(exerId + " " + date + " " + repeats);
            executionRepository.saveExecution(userId, challId, exerId, date, repeats);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }


    @DeleteMapping("/challenges/{chall_id}")
    public ResponseEntity removeChallenge(@PathVariable("chall_id") Long challId) {
        return new ResponseEntity(HttpStatus.OK);       // TODO
    }
}