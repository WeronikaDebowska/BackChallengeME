package com.codecool.backChallengeMe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Executions")
@Getter
@Setter
@EqualsAndHashCode(exclude = "challenge")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Execution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer repeats = 0;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chall_id")
    @JsonBackReference
    private Challenge challenge;

    @ManyToOne
            (fetch = FetchType.LAZY)
    @JoinColumn(name = "exer_id")
    @JsonIgnore
    private Exercise exercise;

    @Transient
    private Long exerId;

    @Transient
    private String exerName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public void setAdditionalData() {
        this.exerId = exercise.getExerciseId();
        this.exerName = exercise.getExerciseName();
    }

    public Execution() {
    }

    public Execution(Integer repeats, Date date, Long exerId) {
        this.repeats = repeats;
        this.date = date;
        this.exerId = exerId;
    }
}
