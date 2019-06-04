package com.codecool.backChallengeMe.services;

import com.codecool.backChallengeMe.DAO.*;
import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
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
    private StatsService statsService;


    @Autowired
    public ResponseService(UserRepository userRepository, ExecutionRepository executionRepository, StatsService statsService) {
        this.userRepository = userRepository;
        this.executionRepository = executionRepository;
        this.statsService = statsService;
    }


    //methods to create response to "/user" url

    public String getStringUserId(Authentication principal) {
        String username = ((MyUserPrincipal) principal.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long id = user.getId();
        return "{\"id\": \"" + id + "\"}";
    }

    //methods to create response to "/users/{user_id}/challenges" url


    public List<ChallengeUserDetails> getAllChallengeUserDetailsResponse(User user) {

        List<ChallengeUserDetails> challengeUserDetailsList = new LinkedList<>();

        for (ChallengeUser challengeUser : user.getChallengesUsersSet().stream().collect(Collectors.toList())) {
            ChallengeUserDetails challengeUserDetails = new ChallengeUserDetails(challengeUser);
            Challenge challenge = challengeUser.getChall();
            double percentage = statsService.getChallRealization(challenge, user);
            challengeUserDetails.setAccomplishmentPercentage(percentage);
            challengeUserDetailsList.add(challengeUserDetails);
        }

        return challengeUserDetailsList;
    }

    //methods to create response to "challenges/{chall_id}" url

    public ChallengeDetails createChallengeBasicResponse(Challenge challenge) {
        ChallengeDetails challengeDetails = new ChallengeDetails(challenge);
//        challengeDetails.setParticipants(createParticipantList(challenge));
        challengeDetails.setExercises(getExerciseList(challenge));
        return challengeDetails;
    }


    //methods to create response to "challenges/{chall_id}/participants" url

    public List<ChallengeUser> createChallengeParticipantsResponse(Challenge challenge) {

        return challenge.getChallengesUsers().stream()
                .peek(ChallengeUser::setProperties)
                .collect(Collectors.toList());
    }


    //methods to create response to "challenges/{chall_id}/exercises" url


    public List<Exercise> getExerciseList(Challenge challenge) {
        return challenge.getChallengesExercisesSet().stream()
                .map(ChallengeExercise::getExer)
                .collect(Collectors.toList());
    }

    //methods to create response to "challenges/{chall_id}/executions" url


    public List<ExecutionDetails> getExecutionDetails(Challenge challenge, User user) {
        List<Execution> executionList = executionRepository.findExecutionsByChallengeAndUser(challenge, user);

        return executionList.stream()
                .map(ExecutionDetails::new)
                .collect(Collectors.toList());
    }


    //utils

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

}
