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
import java.util.concurrent.atomic.AtomicReference;
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

    double countChallAccomplishmentPercent(ChallengeUser challengeUser) {

        double challAccomplishmentPercent = 0;

        Challenge chal = challengeUser.getChall();
        User user = challengeUser.getUser();

        List<Exercise> exerciseList = getExerciseList(challengeUser);
        if (exerciseList.isEmpty()) {
            return challAccomplishmentPercent;
        }
        challAccomplishmentPercent = getChallAccomplishmentPercent(challAccomplishmentPercent, chal, user, exerciseList);

        return challAccomplishmentPercent / exerciseList.size();

    }

    private double getChallAccomplishmentPercent(double challengeAccomplishmentPercentage, Challenge chal, User user, List<Exercise> exerciseList) {
        for (Exercise exercise : exerciseList) {
            Optional<ChallengeExercise> challengeExercise = challengeExerciseRepository.findByChallAndExer(chal, exercise);
            if (challengeExercise.isPresent()) {

                double exerciseAccomplishmentPercentage = getExerAccomplishmentPercent(chal, user, exercise);
                challengeAccomplishmentPercentage += exerciseAccomplishmentPercentage;
            }
        }
        return challengeAccomplishmentPercentage;
    }

    private double getExerAccomplishmentPercent(Challenge chall, User user, Exercise exercise) {
        Optional<ChallengeExercise> challExer = challengeExerciseRepository.findByChallAndExer(chall, exercise);

        AtomicReference<Double> percent = new AtomicReference<>(0.00);

        challExer.ifPresent(
                challengeExercise -> {
                    double goal = challExer.get().getGoal();
                    List<Execution> executions = (executionRepository.findExecutionsByChallengeAndUserAndExercise(chall, user, exercise));
                    percent.set(executions.stream().map(Execution::getRepeats).reduce(0, (x, y) -> x + y) / goal * 100);
                });
        return percent.get();



    }

    private List<Exercise> getExerciseList(ChallengeUser challengeUser) {
        return challengeUser.getChall().getChallengesExercisesSet().stream()
                .map(ChallengeExercise::getExer)
                .collect(Collectors.toList());
    }

}
