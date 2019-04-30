package com.codecool.backChallengeMe.model;


import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Exercises")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Exercise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;


    @OneToMany(mappedBy = "exer")
    @Transient
    private transient Set<ChallengeExercise> challengesExercisesSet;

    @OneToMany(
            mappedBy = "exer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Transient
    private Set<Execution> executionSet;

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
