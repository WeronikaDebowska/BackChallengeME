package com.codecool.backChallengeMe.model.junctionTables;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Exercise;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Challenges_exercises")
@Getter
@Setter
public class ChallengeExercise implements Serializable {

    @EmbeddedId
    private ChallengesExercisesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("challId")
    private Challenge chall;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("exerId")
    private Exercise exer;

    private int goal;

    public ChallengeExercise() {
    }

    public ChallengeExercise(Challenge challenge, Exercise exercise) {
        this.chall = challenge;
        this.exer = exercise;
        this.id = new ChallengesExercisesId(challenge.getId(), exercise.getExerciseId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ChallengeExercise that = (ChallengeExercise) o;
        return Objects.equals(chall, that.chall) &&
                Objects.equals(exer, that.exer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chall, exer);
    }
}
