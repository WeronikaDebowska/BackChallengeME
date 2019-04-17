package com.codecool.backChallengeMe.model;


import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "Executoions")
@Component
@Getter
public class Execution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer repeats = 0;

    @ManyToOne
    private User user;

    @ManyToOne
    private Challenge challenge;

    @ManyToOne
    private Exercise exercise;

    private Date timestamp;
}
