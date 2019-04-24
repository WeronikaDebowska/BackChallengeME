package com.codecool.backChallengeMe.services;


import com.codecool.backChallengeMe.DAO.ChallengeExerciseRepository;
import com.codecool.backChallengeMe.DAO.ChallengeRepository;
import com.codecool.backChallengeMe.DAO.ChallengeUserRepository;
import com.codecool.backChallengeMe.DAO.UserRepository;
import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import com.codecool.backChallengeMe.model.responses.ChallengeDetails;
import com.codecool.backChallengeMe.model.responses.ChallengeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChallengeUserService {

    @Autowired
    private UserRepository userRepository;

    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeUserRepository challengeUserRepository;

    @Autowired
    private ChallengeDetails challengeDetails;

    @Autowired
    private ChallengeExerciseRepository challengeExerciseRepository;


    public void createChallengeUserDetailsResponse(Long id, List<ChallengeUserDetails> allChallengesDetails) {
        User user = userRepository.findUserById(id);
        System.out.println(user.getUsername());
        System.out.println(user.getId());

        List<ChallengeUser> challengeUserSet = challengeUserRepository.findAllByUser(user);

        for (ChallengeUser challengeUser : challengeUserSet) {
            ChallengeUserDetails challengeDetails = new ChallengeUserDetails().setDetails(challengeUser);
            allChallengesDetails.add(challengeDetails);
            System.out.println(challengeUser.getChall().getName());

        }
    }

    public ChallengeDetails createChallengeDetailsResponse(Challenge challenge) {

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
}
