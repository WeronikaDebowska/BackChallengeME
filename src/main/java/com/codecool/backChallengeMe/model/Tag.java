package com.codecool.backChallengeMe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@EqualsAndHashCode
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "tag_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "challenges_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "chall_id"))
    @JsonBackReference
    private List<Challenge> challenges = new LinkedList<>();
}
