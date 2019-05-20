package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Tag;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import lombok.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ChallengeUserDetails extends ChallengeBasicInfo implements Serializable {

    private String userRole;        //TODO change String into enum UserRole and map with DB
    private String challengeStatus; //TODO change String into enum ChallengeStatus and map with DB
    private Integer accomplishmentPercentage;

    public ChallengeUserDetails() {
    }

    public ChallengeUserDetails(ChallengeUser challengeUser) {
        super(challengeUser.getChall());
        this.userRole = challengeUser.getUserRole();
        this.challengeStatus = challengeUser.getChallenge_status();
        ; //TODO count exact percentage from db

    }
}
