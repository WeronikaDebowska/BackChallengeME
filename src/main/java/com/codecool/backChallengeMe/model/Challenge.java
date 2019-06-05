package com.codecool.backChallengeMe.model;


import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.Participation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "Challenges")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"participationList"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Challenge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;
    private Timestamp start;
    private Timestamp finish;

    @ManyToMany(mappedBy = "challenges", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Tag> tagList = new LinkedList<>();


    @OneToMany(mappedBy = "chall", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Participation> participationList = new LinkedList<>();

    @OneToMany(mappedBy = "chall", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ChallengeExercise> exercisesSet = new HashSet<>();

    @OneToMany(
            mappedBy = "challenge",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private Set<Execution> executionSet = new HashSet<>();

    public Challenge() {
    }

    public Challenge(String name, Timestamp start, Timestamp finish, Set<Participation> challengesUsersSet, Set<ChallengeExercise> challengesExercisesSet) {
        this.name = name;
        this.start = start;
        this.finish = finish;
        for (Participation participation : challengesUsersSet) participation.setChall(this);
        for (ChallengeExercise challengesExercise : challengesExercisesSet) challengesExercise.setChall(this);
    }


}
