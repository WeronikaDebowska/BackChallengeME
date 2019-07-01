package com.codecool.backChallengeMe.model;


import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import com.codecool.backChallengeMe.model.junctionTables.Participation;
import com.fasterxml.jackson.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Challenges")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"participationList"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Challenge implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String name;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date start;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date finish;

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

    public Challenge(String name, Date start, Date finish) {
        this.name = name;
        this.start = start;
        this.finish = finish;
    }


}
