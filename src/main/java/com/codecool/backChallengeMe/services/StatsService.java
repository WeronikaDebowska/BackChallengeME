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

    double getChallRealization(ChallengeUser challengeUser) {
        List<Exercise> exerciseList = getExerciseList(challengeUser);
        return exerciseList.isEmpty() ? 0.00 : countChallRealization(challengeUser, exerciseList) / exerciseList.size();
    }

    private double countChallRealization(ChallengeUser challUser, List<Exercise> exerciseList) {
        return exerciseList.stream()
                .map(exer -> challengeExerciseRepository.findByChallAndExer(challUser.getChall(), exer))
                .map(Optional::get)
                .map(ChallengeExercise::getExer)
                .map(exercise -> gettExerRealization(challUser, exercise))
                .reduce(0.00, (x, y) -> x + y);
    }

    private double gettExerRealization(ChallengeUser challUser, Exercise exercise) {
        Optional<ChallengeExercise> challExer = challengeExerciseRepository.findByChallAndExer(challUser.getChall(), exercise);
        return challExer.map(challengeExercise -> countExerRealization(challUser, challExer.get())).orElse(0.00);
    }

    private double countExerRealization(ChallengeUser challUser, ChallengeExercise challExer) {
        Challenge challenge = challUser.getChall();
        User user = challUser.getUser();
        List<Execution> executions = (executionRepository.findExecutionsByChallengeAndUserAndExercise(challenge, user, challExer.getExer()));
        return executions.stream()
                .map(Execution::getRepeats)
                .reduce(0, (x, y) -> x + y)
                .doubleValue() / challExer.getGoal() * 100;
    }

    private List<Exercise> getExerciseList(ChallengeUser challengeUser) {
        return challengeUser.getChall().getChallengesExercisesSet().stream()
                .map(ChallengeExercise::getExer)
                .collect(Collectors.toList());
    }

}
