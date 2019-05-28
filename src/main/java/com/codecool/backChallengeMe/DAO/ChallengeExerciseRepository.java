package com.codecool.backChallengeMe.DAO;

import com.codecool.backChallengeMe.model.Challenge;
import com.codecool.backChallengeMe.model.Exercise;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ChallengeExerciseRepository extends JpaRepository<ChallengeExercise, Long> {

    Optional<ChallengeExercise> findByChallAndExer(Challenge challenge, Exercise exercise);
}
