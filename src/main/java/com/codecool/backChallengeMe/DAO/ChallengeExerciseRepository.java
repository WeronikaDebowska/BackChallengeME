package com.codecool.backChallengeMe.DAO;

import com.codecool.backChallengeMe.model.junctionTables.ChallengeExercise;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChallengeExerciseRepository extends JpaRepository<ChallengeExercise, Long> {
}
