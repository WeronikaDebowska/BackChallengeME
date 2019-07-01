package com.codecool.backChallengeMe.DAO;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Execution;
import com.codecool.backChallengeMe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    List<Execution> findExecutionsByChallengeAndUserOrderByDateDesc(Challenge challenge, User user);

    @Query(
            value = ("Select date, e.exer_id, repeats from executions e " +
                    "where e.chall_id = :challId and user_id = :userId " +
                    "order by date desc, exer_id "),
            nativeQuery = true
    )
    List<Execution> findExecutions(@Param("userId") Long userId,
                                   @Param("challId") Long challId);


    @Query(
            value = "Select sum(repeats) from executions where (chall_id = ?1 and user_id = ?2 and exer_id = ?3) group by exer_id, chall_id, user_id order by date desc",
            nativeQuery = true
    )
    Integer findExecutionsByChallengeAndUserAndExercise1(Long chall_id, Long user_id, Long exer_id);

    @Query(
            value = ("INSERT INTO Executions (user_id, chall_id, exer_id, date, repeats) VALUES (:userId, :challId, :exerId, :date, :repeats)"),
            nativeQuery = true
    )
    void saveExecution(@Param("userId") Long userId,
                       @Param("challId") Long challId,
                       @Param("exerId") Long exerId,
                       @Param("date") Date date,
                       @Param("repeats") int repeats);

}