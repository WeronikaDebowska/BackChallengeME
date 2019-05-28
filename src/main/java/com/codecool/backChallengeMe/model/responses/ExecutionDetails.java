package com.codecool.backChallengeMe.model.responses;

import com.codecool.backChallengeMe.model.Execution;
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
    private Long exerciseId;
    private String exerciseName;

    public ExecutionDetails() {
    }

    public ExecutionDetails(Execution execution) {
        this.id = execution.getId();
        this.repeats = execution.getRepeats();
        this.date = execution.getDate();
        this.exerciseId = execution.getExercise().getExerciseId();
        this.exerciseName = execution.getExercise().getExerciseName();
    }
}
