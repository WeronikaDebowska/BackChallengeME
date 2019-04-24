package com.codecool.backChallengeMe.model;


import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Exercises")
@Getter
public class Exercise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;


    @OneToMany(mappedBy = "exer", cascade = CascadeType.ALL)
    private Set<ChallengeExercise> challengesExercisesSet;

    public Exercise() {
    }

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise ex = (Exercise) o;
        return Objects.equals(name, ex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
