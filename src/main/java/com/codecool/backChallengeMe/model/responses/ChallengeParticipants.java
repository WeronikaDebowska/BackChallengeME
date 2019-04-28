package com.codecool.backChallengeMe.model.responses;

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

}
