package com.codecool.backChallengeMe.model.responses;


import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.User;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
@Getter
@Setter
public class Participant {


    private Long participantId;
    private String participantName;
    private int challengeAcomplishmentProcentage;
    private String role;

    private User user;

    public Participant() {
    }

    public Participant(ChallengeUser challengeUser) {
        User user = challengeUser.getUser();
        this.participantId = user.getId();
        this.participantName = user.getUsername();
        this.role = challengeUser.getUserRole();
    }


    //mocking various  accomplishment percentage TODO count real percentage
    public void setParticipantChallengeAccomplishmentPercentage(int i) {
        this.challengeAcomplishmentProcentage = 50 + i * (-1) ^ i * 5;
    }

}

