package com.codecool.backChallengeMe.model;

import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
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
    private String password;
    public String roles;
    private String username;


    @OneToMany(mappedBy = "user")
    Set<ChallengeUser> challengesUsersSet;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Execution> executionList;

}