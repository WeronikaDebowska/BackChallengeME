package com.codecool.backChallengeMe.model;


import lombok.Getter;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity(name = "Challenges")
@Component
@Getter
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Date start;
    private Date finish;

    @Transient
    private List<User> participantsList;

    @Transient
    private List<Exercise> exerciseList;


}
