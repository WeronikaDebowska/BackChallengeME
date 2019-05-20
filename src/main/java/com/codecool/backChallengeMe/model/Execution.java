package com.codecool.backChallengeMe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity(name = "Executions")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Execution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer repeats = 0;
    private Timestamp date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chall_id")
//    @MapsId("challengeId")
//    @Transient
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exer_id")
//    @MapsId("exerId")
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @MapsId("userId")
//    @Transient
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Execution execution = (Execution) o;
        return Objects.equals(id, execution.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
