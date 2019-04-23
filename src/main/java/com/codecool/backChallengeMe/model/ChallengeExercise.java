package com.codecool.backChallengeMe.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Challengers_exercises")
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
    private Exercise exercise;

    private int goal;

    public ChallengeExercise() {
    }

    public ChallengeExercise(Challenge challenge, Exercise exercise) {
        this.chall = challenge;
        this.exercise = exercise;
        this.id = new ChallengesExercisesId(challenge.getId(), exercise.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ChallengeExercise that = (ChallengeExercise) o;
        return Objects.equals(chall, that.chall) &&
                Objects.equals(exercise, that.exercise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chall, exercise);
    }
}
