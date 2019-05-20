package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Challenge;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ChallengeParticipants {

    private Long chall_Id;
    private String challengeName;
    private List<Participant> participantList = new LinkedList();


    public ChallengeParticipants() {
    }

    public ChallengeParticipants(Long chall_Id, String challengeName) {
        this.chall_Id = chall_Id;
        this.challengeName = challengeName;
    }

    public ChallengeParticipants(Challenge challenge, List<Participant> participantList) {
        this.chall_Id = challenge.getId();
        this.challengeName = challenge.getName();
        this.participantList = participantList;
    }

}
