package com.codecool.backChallengeMe.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity(name = "Users")
@Component
@Getter
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    public String roles;
    private String username;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<ChallengeUser> challengesUsersSet;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}