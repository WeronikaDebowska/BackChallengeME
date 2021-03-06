package com.codecool.backChallengeMe.model;


import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "Exercises")
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Exercise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long exerciseId;

    @Column(name = "name")
    private String exerciseName;

    @Column(name = "description")
    private String exerciseDescription;


    @OneToMany(mappedBy = "exer")
    @JsonIgnore
    private transient Set<ChallengeExercise> challengesExercisesSet;

    @OneToMany(
            mappedBy = "exer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Transient
    @JsonIgnore
    private Set<Execution> executionSet;

    public Exercise() {
    }

    public Exercise(String name, String description) {
        this.exerciseName = name;
        this.exerciseDescription = description;
    }

}
