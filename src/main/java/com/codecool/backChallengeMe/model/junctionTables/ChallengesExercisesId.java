package com.codecool.backChallengeMe.model.junctionTables;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

@Data
public class ChallengesExercisesId implements Serializable {

    @Column(name = "chall_id")
    private Long challId;

    @Column(name = "exer_id")
    private Long exerId;

    public ChallengesExercisesId() {
    }

    public ChallengesExercisesId(Long challId, Long exerId) {
        this.challId = challId;
        this.exerId = exerId;
    }


}
