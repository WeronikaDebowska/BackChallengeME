package com.codecool.backChallengeMe.DAO;

import com.codecool.backChallengeMe.model.ChallengeUser;
import com.codecool.backChallengeMe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {
    Set<ChallengeUser> findAllByUser(User user);

}
