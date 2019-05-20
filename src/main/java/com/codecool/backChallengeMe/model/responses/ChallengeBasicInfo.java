package com.codecool.backChallengeMe.model.responses;


import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ChallengeBasicInfo {

    private Long challengeId;
    private String challengeName;

    private Timestamp start;
    private Timestamp finish;

    private List<String> tagList;

    public ChallengeBasicInfo() {
    }

    public ChallengeBasicInfo(Challenge challenge) {
        this.challengeId = challenge.getId();
        this.challengeName = challenge.getName();
        this.start = challenge.getStart();
        this.finish = challenge.getFinish();
        this.tagList = challenge.getChallengeTagList().stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());
    }
}


