package com.codecool.backChallengeMe.model.junctionTables;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ParticipationId implements Serializable {

    @Column(name = "chall_id")
    private Long challId;

    @Column(name = "user_id")
    private Long userId;

    public ParticipationId() {
    }

    public ParticipationId(Long challId, Long userId) {
        this.challId = challId;
        this.userId = userId;
    }


}
