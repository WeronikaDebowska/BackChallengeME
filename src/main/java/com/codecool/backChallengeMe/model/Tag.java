package com.codecool.backChallengeMe.model;

import lombok.*;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "challenges_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "chall_id"))
    private List<Challenge> challenges = new LinkedList<>();
}
