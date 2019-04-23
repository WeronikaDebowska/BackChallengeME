package com.codecool.backChallengeMe.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChallengesUsersId implements Serializable {

    @Column(name = "chall_id")
    private Long challId;

    @Column(name = "user_id")
    private Long userId;

    public ChallengesUsersId() {
    }

    public ChallengesUsersId(Long challId, Long userId) {
        this.challId = challId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ChallengesUsersId that = (ChallengesUsersId) o;
        return Objects.equals(challId, that.challId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(challId, userId);
    }


}
