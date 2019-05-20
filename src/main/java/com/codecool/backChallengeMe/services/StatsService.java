package com.codecool.backChallengeMe.services;

import com.codecool.backChallengeMe.DAO.ChallengeExerciseRepository;
import com.codecool.backChallengeMe.DAO.ExecutionRepository;
import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Execution;
import com.codecool.backChallengeMe.model.Exercise;
import com.codecool.backChallengeMe.model.User;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private ExecutionRepository executionRepository;
    private ChallengeExerciseRepository challengeExerciseRepository;

    @Autowired
    public StatsService(ExecutionRepository executionRepository, ChallengeExerciseRepository challengeExerciseRepository) {
        this.executionRepository = executionRepository;
        this.challengeExerciseRepository = challengeExerciseRepository;
    }

    public double countParticipantChallengeAccomplishmentPercentage(ChallengeUser challengeUser) {

        double challengeAccomplishmentPercentage = 0;

        Challenge chal = challengeUser.getChall();
        User user = challengeUser.getUser();

        List<Exercise> exerciseList = getExerciseList(challengeUser);
        if (exerciseList.isEmpty()) {
            return challengeAccomplishmentPercentage;
        }
        challengeAccomplishmentPercentage = getChallengeAccomplishmentPercentage(challengeAccomplishmentPercentage, chal, user, exerciseList);

        return challengeAccomplishmentPercentage / exerciseList.size();

    }

    private double getChallengeAccomplishmentPercentage(double challengeAccomplishmentPercentage, Challenge chal, User user, List<Exercise> exerciseList) {
        for (Exercise exercise : exerciseList) {
            Optional<ChallengeExercise> challengeExercise = Optional.ofNullable(challengeExerciseRepository.findByChallAndExer(chal, exercise));
            if (challengeExercise.isPresent()) {

                double exerciseAccomplishmentPercentage = getExerciseAccomplishmentPercentage(chal, user, exercise, challengeExercise.get());
                challengeAccomplishmentPercentage += exerciseAccomplishmentPercentage;
            }
        }
        return challengeAccomplishmentPercentage;
    }

    private double getExerciseAccomplishmentPercentage(Challenge chal, User user, Exercise exercise, ChallengeExercise chalExer) {
        double goal = getExerciseGoal(chalExer);
        Optional<List<Execution>> executions = Optional.ofNullable(executionRepository.findExecutionsByChallengeAndUserAndExercise(chal, user, exercise));

        double exerciseAccomplishmentPercentage = 0;
        if (executions.isPresent()) {
            for (Execution execution : executions.get()) {
                int executionsMade = execution.getRepeats();
                exerciseAccomplishmentPercentage += executionsMade / goal * 100;
            }
        }
        return exerciseAccomplishmentPercentage;
    }

    private double getExerciseGoal(ChallengeExercise challengeExercise) {
        return (double) challengeExercise.getGoal();
    }

    private List<Exercise> getExerciseList(ChallengeUser challengeUser) {
        return challengeUser.getChall().getChallengesExercisesSet().stream()
                .map(ChallengeExercise::getExer)
                .collect(Collectors.toList());
    }

}
