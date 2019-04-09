package com.codecool.springsecangtutorial.DAO;

import com.codecool.springsecangtutorial.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String name);
}
