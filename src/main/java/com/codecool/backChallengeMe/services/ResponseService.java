package com.codecool.backChallengeMe.services;


import com.codecool.backChallengeMe.DAO.*;
import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import com.codecool.backChallengeMe.model.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class ResponseService {

    private UserRepository userRepository;
    private ChallengeRepository challengeRepository;
    private ExecutionRepository executionRepository;
    private ChallengeUserRepository challengeUserRepository;
    private ChallengeExerciseRepository challengeExerciseRepository;


    @Autowired
    public ResponseService(UserRepository userRepository, ChallengeRepository challengeRepository, ExecutionRepository executionRepository, ChallengeUserRepository challengeUserRepository, ChallengeExerciseRepository challengeExerciseRepository) {
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.executionRepository = executionRepository;
        this.challengeUserRepository = challengeUserRepository;
        this.challengeExerciseRepository = challengeExerciseRepository;
    }


    //methods to create response to "/user" url

    public String getStringUserId(Authentication principal) {
        String username = ((MyUserPrincipal) principal.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long id = user.getId();
        return "{\"id\": \"" + id + "\"}";
    }

    //methods to create response to "/users/{user_id}/challenges" url

    public ResponseEntity<List<ChallengeUserDetails>> createUserAllChallengesDetailsResponse(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isPresent()) {
            List<ChallengeUserDetails> allChallengesDetails = getAllChallengeUserDetailsResponse(user_id);
            return new ResponseEntity<>(allChallengesDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    private List<ChallengeUserDetails> getAllChallengeUserDetailsResponse(Long id) {
        List<ChallengeUserDetails> allChallengesDetails = new LinkedList<>();
        User user = userRepository.findUserById(id);
        List<ChallengeUser> challengeUserSet = challengeUserRepository.findAllByUser(user);
        for (ChallengeUser challengeUser : challengeUserSet) {
            ChallengeUserDetails challengeDetails = new ChallengeUserDetails().setDetails(challengeUser);
            allChallengesDetails.add(challengeDetails);
        }
        return allChallengesDetails;
    }

    //methods to create response to "challenges/{chall_id}" url

    public ResponseEntity<ChallengeDetails> createChallengeDetailsResponse(Long chall_id) {
        Optional<Challenge> challenge = getChallengeById(chall_id);
        if (challenge.isPresent()) {
            ChallengeDetails challengeDetails = getChallengeDetails(challenge.get());
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private ChallengeDetails getChallengeDetails(Challenge challenge) {

        Map<Long, String> participants = new HashMap<>();
        for (ChallengeUser challengeUser : challengeUserRepository.findAllByChall(challenge)) {
            User user = challengeUser.getUser();
            participants.put(user.getId(), user.getUsername());
        }

        Map<Long, String> exercises = new HashMap<>();
        for (ChallengeExercise challengeExercise : challengeExerciseRepository.findAllByChall(challenge)) {
            Exercise exer = challengeExercise.getExer();
            exercises.put(exer.getId(), exer.getName());
        }

        return new ChallengeDetails(challenge.getId(), challenge.getName(), participants, exercises);
    }


    //methods to create response to "challenges/{chall_id}/paticipants" url

    public ResponseEntity<ChallengeParticipants> createChallengeParticipantsResponse(Long chall_id) {
        Optional<Challenge> challenge = getChallengeById(chall_id);
        if (challenge.isPresent()) {
            ChallengeParticipants challengeParticipants = getChallengeParticipants(challenge.get());
            return new ResponseEntity<>(challengeParticipants, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ChallengeParticipants getChallengeParticipants(Challenge challengeToDisplay) {
        List<ChallengeUser> challengeUsers = challengeUserRepository.findAllByChall(challengeToDisplay);
        List<Participant> participantsList = new LinkedList<>();
        for (ChallengeUser challengeUser : challengeUsers) {
            Participant participant = new Participant(challengeUser.getUser().getId(), challengeUser.getUser().getUsername(), challengeUser.getUserRole());
            participantsList.add(participant);
        }
        ChallengeParticipants challengeParticipants = new ChallengeParticipants(challengeToDisplay.getId(), challengeToDisplay.getName());
        setParticipantsChallengeAccomplishmentPercentage(participantsList);

        challengeParticipants.setParticipantList(participantsList);
        return challengeParticipants;
    }

    //mocking various  accomplishment percentage TODO count real percentage
    private void setParticipantsChallengeAccomplishmentPercentage(List<Participant> participantsList) {
        int i = 0;
        for (Participant participant : participantsList) {
            participant.setChallengeAcomplishmentProcentage(50 + i * (-1) ^ i * 5);
            i++;
        }
    }

    //methods to create response to "challenges/{chall_id}/exercises" url

    public ResponseEntity<List<Exercise>> createChallengeExerciseListResponse(Long chall_id) {
        Optional<Challenge> challenge = getChallengeById(chall_id);
        if (challenge.isPresent()) {
            List<Exercise> exercisesToDisplay = getExerciseList(challenge.get());
            return new ResponseEntity<>(exercisesToDisplay, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private List<Exercise> getExerciseList(Challenge challenge) {
        List<Exercise> exercisesToDisplay = new LinkedList<>();
        List<ChallengeExercise> challengeExercisesList = challengeExerciseRepository.findAllByChall(challenge);
        for (ChallengeExercise challengeExercise : challengeExercisesList) {
            exercisesToDisplay.add(challengeExercise.getExer());
        }
        return exercisesToDisplay;
    }

    //methods to create response to "challenges/{chall_id}/executions" url


    public ResponseEntity<List<ExecutionDetails>> createChallengeUserExecution(Long user_id, Long chall_id) {
        Optional<Challenge> challenge = getChallengeById(chall_id);
        Optional<User> user = userRepository.findById(user_id);
        if (challenge.isPresent() && user.isPresent()) {
            List<ExecutionDetails> executionDetailsList = getExecutionDetails(challenge.get(), user.get());
            return new ResponseEntity<>(executionDetailsList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private List<ExecutionDetails> getExecutionDetails(Challenge challenge, User user) {
        List<Execution> executionList = executionRepository.findExecutionsByChallengeAndUser(challenge, user);
        List<ExecutionDetails> executionDetailsList = new LinkedList<>();
        for (Execution execution : executionList) {
            executionDetailsList.add(new ExecutionDetails(execution.getId(), execution.getRepeats(), execution.getDate(), execution.getExercise()));
        }
        return executionDetailsList;
    }


    //utils

    public Optional<Challenge> getChallengeById(@PathVariable("chall_id") Long chall_id) {
        return challengeRepository.findById(chall_id);
    }

}
