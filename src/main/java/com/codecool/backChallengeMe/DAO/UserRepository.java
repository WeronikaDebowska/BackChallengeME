package com.codecool.backChallengeMe.DAO;

import com.codecool.backChallengeMe.model.User;
import com.codecool.backChallengeMe.model.junctionTables.ChallengeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);
}
