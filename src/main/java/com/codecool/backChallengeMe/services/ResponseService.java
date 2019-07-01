package com.codecool.backChallengeMe.services;

import com.codecool.backChallengeMe.DAO.ExecutionRepository;
import com.codecool.backChallengeMe.DAO.ExerciseRepository;
import com.codecool.backChallengeMe.DAO.UserRepository;
import com.codecool.backChallengeMe.model.*;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.Participation;
import com.codecool.backChallengeMe.model.responses.ParticipationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingByConcurrent;

@Service
public class ResponseService {

    private UserRepository userRepository;
    private ExecutionRepository executionRepository;
    private ExerciseRepository exerciseRepository;


    @Autowired
    public ResponseService(UserRepository userRepository,
                           ExecutionRepository executionRepository,
                           ExerciseRepository exerciseRepository) {
        this.userRepository = userRepository;
        this.executionRepository = executionRepository;
        this.exerciseRepository = exerciseRepository;
    }


    //methods to create response to "/user" url

    public String getStringUserId(Authentication principal) {
        String username = ((MyUserPrincipal) principal.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        Long id = user.getId();
        return "{\"userId\": \"" + id + "\"}";
    }

    //methods to create response to "/users/{user_id}/challenges" url


    public List<ParticipationDetails> getAllChallengeUserDetailsResponse(User user) {

        return user.getParticipationSet().stream()
                .map(ParticipationDetails::new)
                .collect(Collectors.toList());
    }

    //methods to create response to "challenges/{chall_id}" url

    public Challenge createChallengeBasicResponse(Challenge challenge) {
        return challenge;
    }


    //methods to create response to "challenges/{chall_id}/participants" url

    public List<ParticipationDetails> createParticipantsResponse(Challenge challenge) {

        return challenge.getParticipationList().stream()
                .sorted(Comparator.comparing(Participation::getProgress).reversed())
                .map(ParticipationDetails::new)
                .collect(Collectors.toList());
    }


    //methods to create response to "challenges/{chall_id}/exercises" url


    public List<Exercise> getExerciseList(Challenge challenge) {
        return challenge.getExercisesSet().stream()
                .map(ChallengeExercise::getExer)
                .sorted(Comparator.comparingLong(Exercise::getExerciseId))
                .collect(Collectors.toList());
    }

    //methods to create response to "challenges/{chall_id}/executions" url


    public Map<Date, List<Execution>> getExecutionDetails(Challenge challenge, User user) {

        Map<Date, List<Execution>> executions = mapChallengesByDate(challenge, user);
        List<Exercise> exercises = getExerciseList(challenge);
        completeWithZerosIfNeeded(executions, exercises);
        return executions;
    }

    private void completeWithZerosIfNeeded(Map<Date, List<Execution>> executions, List<Exercise> exercises) {
        List<Execution> execList;

        for (Date date : executions.keySet()) {
            List<Execution> execListWithZeros = new LinkedList<>();
            execList = executions.get(date);
            for (Exercise exer : exercises) {
                boolean found = false;
                for (Execution exec : execList) {
                    if (exec.getExerId().equals(exer.getExerciseId())) {
                        execListWithZeros.add(exec);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    execListWithZeros.add(new Execution(0, date, exer.getExerciseId()));
                }
            }
            executions.put(date, execListWithZeros);
        }
    }

    private Map<Date, List<Execution>> mapChallengesByDate(Challenge challenge, User user) {
        return executionRepository.findExecutionsByChallengeAndUserOrderByDateDesc(challenge, user)
                .stream()
                .peek(Execution::setAdditionalData)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Execution::getDate))
                .collect(groupingByConcurrent(Execution::getDate));
    }


    //utils

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

}
