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
@EqualsAndHashCode(exclude = "user")
public class ChallengeUser implements Serializable {

    @EmbeddedId
    private ChallengesUsersId id;

    @ManyToOne
//            (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("challId")
    private Challenge chall;

    @ManyToOne
//            (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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


}
