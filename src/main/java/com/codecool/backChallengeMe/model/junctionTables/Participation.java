package com.codecool.backChallengeMe.model.junctionTables;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.User;
//import com.codecool.backChallengeMe.model.enums.ChallengeRole;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

//import lombok.Getter;

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
    private Double progress;

    @Column(name = "challenge_status")
    private String status;

    @Transient
    private String username;

    @Transient
    private Long userId;

    public void setProperties() {
        this.username = user.getUsername();
        this.userId = user.getId();
    }

    public Participation() {
    }

    public Participation(Challenge challenge, User user) {
        this.chall = challenge;
        this.user = user;
        this.id = new ParticipationId(challenge.getId(), user.getId());
        setProperties();
    }

    public enum ChallengeRole {
        host,
        participant;
    }

}
