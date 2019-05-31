package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Exercise;
import com.codecool.backChallengeMe.model.Tag;
import com.codecool.backChallengeMe.model.User;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ChallengeDetails extends ChallengeBasicInfo implements Serializable {



    private List<String> tagList;
    List<Exercise> exercises = new LinkedList<>();
    private List<User> participants = new LinkedList<>();


    public ChallengeDetails() {
    }

    public ChallengeDetails(Challenge challenge) {
        super(challenge);
        participants = challenge.getChallengesUsers().stream()
                .map(ChallengeUser::getUser)
                .collect(Collectors.toList());
    }
}
