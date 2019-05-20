package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Tag;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import lombok.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ChallengeUserDetails implements Serializable {

    private String challengeName;
    private Long challengeId;
    private String userRole;        //TODO change String into enum UserRole and map with DB
    private String challengeStatus; //TODO change String into enum ChallengeStatus and map with DB
    private Date start;
    private Date finish;
    private Integer accomplishmentPercentage;
    private List<Tag> tagList = new LinkedList();

    public ChallengeUserDetails() {
    }

    public ChallengeUserDetails(ChallengeUser challengeUser) {
        this.challengeName = challengeUser.getChall().getName();
        this.challengeId = challengeUser.getChall().getId();
        this.userRole = challengeUser.getUserRole();
        this.challengeStatus = challengeUser.getChallenge_status();
        this.start = challengeUser.getChall().getStart();
        this.finish = challengeUser.getChall().getFinish();
        this.accomplishmentPercentage = 50; //TODO count exact percentage from db
        this.tagList = challengeUser.getChall().getChallengeTagList();
    }
}
