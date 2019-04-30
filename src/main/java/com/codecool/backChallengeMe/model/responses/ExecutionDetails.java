package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Exercise;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Component
@Scope(value = "prototype")
@Getter
@Setter
public class ExecutionDetails implements Serializable {

    private Long id;
    private Integer repeats;
    private Timestamp date;
    private Exercise exercise;

    public ExecutionDetails() {
    }

    public ExecutionDetails(Long id, Integer repeats, Timestamp date, Exercise exercise) {
        this.id = id;
        this.repeats = repeats;
        this.date = date;
        this.exercise = exercise;
    }
}
