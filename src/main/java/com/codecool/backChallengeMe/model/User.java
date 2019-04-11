package com.codecool.backChallengeMe.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Entity(name = "Users")
@Component
@Getter
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    public String roles;
    private String username;



}