package com.codecool.backChallengeMe.model.responses;


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

    public Participant() {
    }

    public Participant(Long participantId, String participantName, String role) {
        this.participantId = participantId;
        this.participantName = participantName;
        this.role = role;

    }
}
