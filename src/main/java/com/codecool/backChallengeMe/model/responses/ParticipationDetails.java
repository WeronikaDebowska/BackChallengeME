package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Tag;
import com.codecool.backChallengeMe.model.junctionTables.Participation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ParticipationDetails {

    private Long challengeId;
    private String challengeName;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date start;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date finish;

    private List<String> tagList = new LinkedList<>();
    private Long userId;
    private String userName;
    private Participation.ChallengeRole userRole;
    private Participation.ChallengeStatus challengeStatus;
    private Double challengeRealization;



    public ParticipationDetails() {
    }

    public ParticipationDetails(Participation participation) {

        Challenge chall = participation.getChall();

        this.challengeId = chall.getId();
        this.challengeName = chall.getName();
        this.start = chall.getStart();
        this.finish = chall.getFinish();
        this.tagList = chall.getTagList().stream().map(Tag::getName).collect(Collectors.toList());
        this.userId = participation.getUser().getId();
        this.userName = participation.getUser().getUsername();
        this.userRole = participation.getUserRole();
        this.challengeStatus = participation.getStatus();
        this.challengeRealization = participation.getProgress();

    }

}
