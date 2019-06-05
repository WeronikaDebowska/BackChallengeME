package com.codecool.backChallengeMe.services;

import com.codecool.backChallengeMe.DAO.*;
import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.Participation;
import com.codecool.backChallengeMe.model.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    private UserRepository userRepository;
    private ExecutionRepository executionRepository;
//    private StatsService statsService;


    @Autowired
    public ResponseService(UserRepository userRepository, ExecutionRepository executionRepository) {
        this.userRepository = userRepository;
        this.executionRepository = executionRepository;
//        this.statsService = statsService;
    }


    //methods to create response to "/user" url

    public String getStringUserId(Authentication principal) {
        String username = ((MyUserPrincipal) principal.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long id = user.getId();
        return "{\"id\": \"" + id + "\"}";
    }

    //methods to create response to "/users/{user_id}/challenges" url


    public List<ParticipationDetails> getAllChallengeUserDetailsResponse(User user) {

        return user.getParticipationSet().stream()
                .peek(Participation::setProperties)
                .map(ParticipationDetails::new)
                .collect(Collectors.toList());
    }

    //methods to create response to "challenges/{chall_id}" url

    public Challenge createChallengeBasicResponse(Challenge challenge) {
//        ChallengeDetails challengeDetails = new ChallengeDetails(challenge);
//        challengeDetails.setExercises(getExerciseList(challenge));
        return challenge;
    }


    //methods to create response to "challenges/{chall_id}/participants" url

    public List<Participation> createParticipantsResponse(Challenge challenge) {

        return challenge.getParticipationList().stream()
                .peek(Participation::setProperties)
                .collect(Collectors.toList());
    }


    //methods to create response to "challenges/{chall_id}/exercises" url


    public List<Exercise> getExerciseList(Challenge challenge) {
        return challenge.getExercisesSet().stream()
                .map(ChallengeExercise::getExer)
                .collect(Collectors.toList());
    }

    //methods to create response to "challenges/{chall_id}/executions" url


    public List<Execution> getExecutionDetails(Challenge challenge, User user) {
        return executionRepository.findExecutionsByChallengeAndUser(challenge, user).stream()
                .peek(Execution::setAdditionalData)
                .collect(Collectors.toList());
    }


    //utils

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

}
