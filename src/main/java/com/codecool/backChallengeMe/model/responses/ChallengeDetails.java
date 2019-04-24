package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.DAO.ChallengeExerciseRepository;
import com.codecool.backChallengeMe.DAO.ChallengeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ChallengeDetails implements Serializable {

    private Long challengeId;
    private String challengeName;
    private Map<Long, String> challengeExercises = new HashMap<>();
    private Map<Long, String> participants = new HashMap<>();


    public ChallengeDetails() {
    }


    public ChallengeDetails(Long challengeId, String challengeName, Map<Long, String> challengeExercises, Map<Long, String> participants) {
        this.challengeId = challengeId;
        this.challengeName = challengeName;
        this.challengeExercises = challengeExercises;
        this.participants = participants;
    }
}
