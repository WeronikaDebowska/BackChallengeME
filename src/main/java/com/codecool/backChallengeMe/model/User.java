package com.codecool.backChallengeMe.model;

import com.codecool.backChallengeMe.model.junctionTables.Participation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity(name = "Users")
@Component
@Getter
@Setter
@EqualsAndHashCode(exclude = {"participationSet", "executionList"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    public String roles;        //admin or user

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    Set<Participation> participationSet;

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
}