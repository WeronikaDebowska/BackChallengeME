package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ChallengeDetails implements Serializable {

    private Long challengeId;
    private String challengeName;

    private Timestamp start;
    private Timestamp finish;

    private List<Tag> tagList;

    private Map<Long, String> challengeExercises = new HashMap<>();
    private Map<Long, String> participants = new HashMap<>();


    public ChallengeDetails() {
    }


    public ChallengeDetails(Challenge challenge, Map<Long, String> challengeExercises, Map<Long, String> participants) {
        this.challengeId = challenge.getId();
        this.challengeName = challenge.getName();
        this.challengeExercises = challengeExercises;
        this.participants = participants;
        this.start = challenge.getStart();
        this.finish = challenge.getFinish();
        this.tagList = challenge.getChallengeTagList();
    }
}
