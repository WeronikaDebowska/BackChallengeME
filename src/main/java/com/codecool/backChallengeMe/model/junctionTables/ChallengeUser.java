package com.codecool.backChallengeMe.model.junctionTables;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.User;
//import com.codecool.backChallengeMe.model.enums.ChallengeRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

//import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Challenges_users")
@Setter
@Getter
@EqualsAndHashCode(exclude = "user")
@JsonIgnoreProperties({"chall", "user"})
public class ChallengeUser implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private ChallengesUsersId id;

    @ManyToOne
            (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("challId")
    @JsonBackReference
    private Challenge chall;

    @ManyToOne
            (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("userId")
    @JsonBackReference
    private User user;
    @Enumerated(EnumType.STRING)
    private ChallengeRole userRole;
    private Double progress;

    @Column(name = "challenge_status")
    private String challengeStatus;
    @Transient
    private String username;
    @Transient
    private Long userId;

    public void setProperties() {
        this.username = user.getUsername();
        this.userId = user.getId();
    }

    public ChallengeUser() {
    }

    public ChallengeUser(Challenge challenge, User user) {
        this.chall = challenge;
        this.user = user;
        this.id = new ChallengesUsersId(challenge.getId(), user.getId());
    }

    public enum ChallengeRole {
        host,
        participant;
    }

}
