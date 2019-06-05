package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Tag;
import com.codecool.backChallengeMe.model.User;
import com.codecool.backChallengeMe.model.junctionTables.Participation;
import lombok.*;

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

    private Long id;
    private String name;
    private Date start;
    private Date finish;
    private List<String> tagList = new LinkedList<>();
    private Participation.ChallengeRole userRole;
    private String status;
    private Double progress;



    public ParticipationDetails() {
    }

    public ParticipationDetails(Participation participation) {

        Challenge chall = participation.getChall();

        this.id = chall.getId();
        this.name = chall.getName();
        this.start = chall.getStart();
        this.finish = chall.getFinish();
        this.tagList = chall.getTagList().stream().map(Tag::getName).collect(Collectors.toList());
        this.userRole = participation.getUserRole();
        this.status = participation.getStatus();
        this.progress = participation.getProgress();

    }

}
