package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.junctionTables.Participation;
import lombok.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ParticipationDetails {

    private Participation participation;
    private Challenge challenge;

    public ParticipationDetails() {
    }

    public ParticipationDetails(Participation participation) {

        this.participation = participation;
        this.challenge = participation.getChall();

    }

}
