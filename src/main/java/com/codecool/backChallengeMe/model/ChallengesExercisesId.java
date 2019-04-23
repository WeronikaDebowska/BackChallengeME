package com.codecool.backChallengeMe.model;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ChallengesExercisesId that = (ChallengesExercisesId) o;
        return Objects.equals(challId, that.challId) &&
                Objects.equals(exerId, that.exerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(challId, exerId);
    }


}
