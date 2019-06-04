package com.codecool.backChallengeMe.model.responses;

//import com.codecool.backChallengeMe.model.enums.ChallengeRole;

import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser.ChallengeRole;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import lombok.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;


@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ChallengeUserDetails extends ChallengeBasicInfo implements Serializable {

    @Enumerated(EnumType.STRING)
    private ChallengeRole userRole;        //TODO change String into enum UserRole and map with DB
    private String challengeStatus; //TODO change String into enum ChallengeStatus and map with DB
    private Double accomplishmentPercentage = 0.0;

    public ChallengeUserDetails() {
    }

    public ChallengeUserDetails(ChallengeUser challengeUser) {
        super(challengeUser.getChall());
        this.userRole = challengeUser.getUserRole();
        this.challengeStatus = challengeUser.getChallengeStatus();

    }

}
