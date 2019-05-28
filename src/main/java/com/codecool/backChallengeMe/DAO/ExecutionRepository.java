package com.codecool.backChallengeMe.DAO;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Execution;
import com.codecool.backChallengeMe.model.Exercise;
import com.codecool.backChallengeMe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    List<Execution> findExecutionsByChallengeAndUser(Challenge challenge, User user);

    List<Execution> findExecutionsByChallengeAndUserAndExercise(Challenge challenge, User user, Exercise exercise);

//    @Query(value = "Select * from executions e where e.chall_id = :challId and e.user_id = :userId",
//        nativeQuery = true)
//    List<Execution> findAllExecutionsByChallengeAndUser(@Param("challId") Long challId, @Param("userId") Long userId);

}
