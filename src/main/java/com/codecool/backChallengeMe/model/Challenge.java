package com.codecool.backChallengeMe.model;


import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Challenge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;
    private Timestamp start;
    private Timestamp finish;

    @ManyToMany(mappedBy = "taggedChallengesList")
    private List<Tag> challengeTagList;


    @OneToMany(mappedBy = "chall", cascade = CascadeType.ALL)
    private List<ChallengeUser> challengesUsers = new LinkedList<>();

    @OneToMany(mappedBy = "chall", cascade = CascadeType.ALL)
    private Set<ChallengeExercise> challengesExercisesSet = new HashSet<>();

    @OneToMany(
            mappedBy = "challenge",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Challenge challenge = (Challenge) o;
        return Objects.equals(id, challenge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
