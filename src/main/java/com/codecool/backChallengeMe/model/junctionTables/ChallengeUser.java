package com.codecool.backChallengeMe.model.junctionTables;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.User;
import lombok.*;

//import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Challenges_users")
@Getter
@Setter
public class ChallengeUser implements Serializable {

    @EmbeddedId
    private ChallengesUsersId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("challId")
    private Challenge chall;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    private String userRole;

    @Column(name = "challenge_status")
    private String challengeStatus;

    public ChallengeUser() {
    }

    public ChallengeUser(Challenge challenge, User user) {
        this.chall = challenge;
        this.user = user;
        this.id = new ChallengesUsersId(challenge.getId(), user.getId());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;
        ChallengeUser that = (ChallengeUser) o;
        return Objects.equals(chall, that.chall) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chall, user);
    }

}
