package com.codecool.backChallengeMe.model.junctionTables;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Exercise;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Challenges_exercises")
@Getter
@Setter
@EqualsAndHashCode(exclude = "chall")
public class ChallengeExercise implements Serializable {

    @EmbeddedId
    private ChallengesExercisesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("challId")
    private Challenge chall;

    @ManyToOne
            (fetch = FetchType.LAZY)
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

}
