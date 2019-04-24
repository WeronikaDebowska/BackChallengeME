package com.codecool.backChallengeMe.DAO;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeExerciseRepository extends JpaRepository<ChallengeExercise, Long> {

    //    List <ChallengeExercise> findChallengeExercisesByChall(Challenge challenge);
    List<ChallengeExercise> findAllByChall(Challenge challenge);
}
