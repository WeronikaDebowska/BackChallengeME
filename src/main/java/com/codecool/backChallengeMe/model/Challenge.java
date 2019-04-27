package com.codecool.backChallengeMe.model;


import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Challenges")
@Getter
@Setter
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
    private Set<ChallengeUser> challengesUsersSet = new HashSet<>();

    @OneToMany(mappedBy = "chall", cascade = CascadeType.ALL)
    private Set<ChallengeExercise> challengesExercisesSet = new HashSet<>();

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
