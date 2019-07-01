package com.codecool.backChallengeMe.model.junctionTables;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Challenges_users")
@Setter
@Getter
@EqualsAndHashCode
@JsonIgnoreProperties({"chall", "user"})
public class Participation {

    @EmbeddedId
    @JsonIgnore
    private ParticipationId id;

    @ManyToOne
            (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("challId")
    @JsonManagedReference
    private Challenge chall;

    @ManyToOne
            (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("userId")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private ChallengeRole userRole;

    @Column(name = "challenge_status")
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    private Double progress;


    public Participation() {
    }

    public Participation(Challenge challenge, User user, ChallengeStatus status, ChallengeRole role) {
        this.chall = challenge;
        this.user = user;
        this.id = new ParticipationId(challenge.getId(), user.getId());
        this.userRole = role;
        this.status = status;
        this.progress = 0.00;

    }


    public enum ChallengeRole {
        host,
        participant;
    }

    public enum ChallengeStatus {
        accepted,
        rejected,
        pending;
    }

}
