package com.codecool.backChallengeMe.DAO;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import com.codecool.backChallengeMe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {
    List<ChallengeUser> findAllByUser(User user);

    List<ChallengeUser> findAllByChall(Challenge challenge);

}
