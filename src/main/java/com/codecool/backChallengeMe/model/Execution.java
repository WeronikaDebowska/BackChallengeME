//package com.codecool.backChallengeMe.model;
//
//import lombok.Getter;
//
//import javax.persistence.*;
//import java.sql.Date;
//
//@Entity(name = "Executoions")
//@Getter
//public class Execution {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    private Integer repeats = 0;
//
//    @ManyToOne(targetEntity = User.class)
//    @JoinColumn(name="user_id")
//    private User user;
//
//    @ManyToOne(targetEntity = Challenge.class)
//    @JoinColumn(name="chall_id")
//    private Challenge challenge;
//
////    @ManyToOne(targetEntity = Exercise.class)
////    @JoinColumn(name="exer_id")
//    private Exercise exercise;
//
//    private Date timestamp;
//}
