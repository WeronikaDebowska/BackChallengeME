package com.codecool.backChallengeMe.model;


import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "Challenges")
@Getter
@Setter
@EqualsAndHashCode
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
    @JsonBackReference
    private List<Tag> challengeTagList = new LinkedList<>();


    @OneToMany(mappedBy = "chall", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ChallengeUser> challengesUsers = new LinkedList<>();

    @OneToMany(mappedBy = "chall", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<ChallengeExercise> challengesExercisesSet = new HashSet<>();

    @OneToMany(
            mappedBy = "challenge",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Transient
    private Set<Execution> executionSet = new HashSet<>();

    public Challenge() {
    }

    public Challenge(String name, Timestamp start, Timestamp finish, Set<ChallengeUser> challengesUsersSet, Set<ChallengeExercise> challengesExercisesSet) {
        this.name = name;
        this.start = start;
        this.finish = finish;
        for (ChallengeUser challengeUser : challengesUsersSet) challengeUser.setChall(this);
        for (ChallengeExercise challengesExercise : challengesExercisesSet) challengesExercise.setChall(this);
    }


}
