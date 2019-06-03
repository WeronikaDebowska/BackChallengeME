package com.codecool.backChallengeMe.model;

import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity(name = "Users")
@Component
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    public String roles;        //admin or user

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    Set<ChallengeUser> challengesUsersSet;

    private String username;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    List<Execution> executionList;
    @JsonIgnore
    private String password;
    @Transient
    private double challengeRealization;
    @Transient
    private String challengeRole;        //host or participant

}