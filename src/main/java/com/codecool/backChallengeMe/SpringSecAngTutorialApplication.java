package com.codecool.backChallengeMe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@SpringBootConfiguration
@EnableJpaRepositories("com.codecool.backChallengeMe.DAO")
@Slf4j
public class SpringSecAngTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecAngTutorialApplication.class, args);
        log.info("start");
    }
}
