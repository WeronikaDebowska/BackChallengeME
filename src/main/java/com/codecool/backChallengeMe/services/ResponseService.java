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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    private UserRepository userRepository;
    private ChallengeRepository challengeRepository;
    private ExecutionRepository executionRepository;


    @Autowired
    public ResponseService(UserRepository userRepository, ChallengeRepository challengeRepository, ExecutionRepository executionRepository) {
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.executionRepository = executionRepository;
    }


    //methods to create response to "/user" url

    public String getStringUserId(Authentication principal) {
        String username = ((MyUserPrincipal) principal.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long id = user.getId();
        return "{\"id\": \"" + id + "\"}";
    }

    //methods to create response to "/users/{user_id}/challenges" url

    public ResponseEntity<List<ChallengeUserDetails>> createUserAllChallengesDetailsResponse(Long userId) {
        Optional<User> user = getUserById(userId);
        if (user.isPresent()) {
            List<ChallengeUserDetails> allChallengesDetails = getAllChallengeUserDetailsResponse(user.get());
            return new ResponseEntity<>(allChallengesDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<ChallengeUserDetails> getAllChallengeUserDetailsResponse(User user) {
        return user.getChallengesUsersSet().stream()
                .map(ChallengeUserDetails::new)
                .collect(Collectors.toList());
    }

    //methods to create response to "challenges/{chall_id}" url

    public ResponseEntity<ChallengeDetails> createChallengeBasicResponse(Long challId) {
        Optional<Challenge> challenge = getChallengeById(challId);
        if (challenge.isPresent()) {
            ChallengeDetails challengeDetails = new ChallengeDetails(challenge.get());
            challengeDetails.setParticipants(createParticipantList(challenge.get()));
            challengeDetails.setExercises(getExerciseList(challenge.get()));
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //methods to create response to "challenges/{chall_id}/paticipants" url

    public ResponseEntity<ChallengeDetails> createChallengeParticipantsResponse(Long chall_id) {
        Optional<Challenge> challenge = getChallengeById(chall_id);
        if (challenge.isPresent()) {
            ChallengeDetails challengeDetails = new ChallengeDetails(challenge.get());
            challengeDetails.setParticipants(createParticipantList(challenge.get()));
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    private ChallengeParticipants getChallengeParticipants(Challenge challengeToDisplay) {
//        List<Participant> participantList = createParticipantList(challengeToDisplay);
//        setAccomplishmentToAllParticipants(participantList);
//        return new ChallengeParticipants(challengeToDisplay, participantList);
//    }

//    private void setAccomplishmentToAllParticipants(List<Participant> participantsList) {
//        participantsList.forEach(participant -> participant.setChallengeAcomplishmentProcentage(52));
//    }

    private List<Participant> createParticipantList(Challenge challenge) {
        return challenge.getChallengesUsers().stream()
                .map(Participant::new)
                .collect(Collectors.toList());
    }


    //methods to create response to "challenges/{chall_id}/exercises" url

    public ResponseEntity<ChallengeDetails> createChallengeExerciseListResponse(Long challId) {
        Optional<Challenge> challenge = getChallengeById(challId);
        if (challenge.isPresent()) {
            ChallengeDetails challengeDetails = new ChallengeDetails(challenge.get());
            challengeDetails.setExercises(getExerciseList(challenge.get()));
            return new ResponseEntity<>(challengeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<Exercise> getExerciseList(Challenge challenge) {
        return challenge.getChallengesExercisesSet().stream()
                .map(ChallengeExercise::getExer)
                .collect(Collectors.toList());
    }

    //methods to create response to "challenges/{chall_id}/executions" url


    public ResponseEntity<List<ExecutionDetails>> createChallengeUserExecution(Long userId, Long challId) {
        Optional<Challenge> challenge = getChallengeById(challId);
        Optional<User> user = getUserById(userId);
        if (challenge.isPresent() && user.isPresent()) {
            List<ExecutionDetails> executionDetailsList = getExecutionDetails(challenge.get(), user.get());
            return new ResponseEntity<>(executionDetailsList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    private List<ExecutionDetails> getExecutionDetails(Challenge challenge, User user) {
        List<Execution> executionList = executionRepository.findExecutionsByChallengeAndUser(challenge, user);
        return executionList.stream()
                .map(ExecutionDetails::new)
                .collect(Collectors.toList());
    }


    //utils

    private Optional<Challenge> getChallengeById(Long challId) {
        return challengeRepository.findById(challId);
    }

    private Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

}
