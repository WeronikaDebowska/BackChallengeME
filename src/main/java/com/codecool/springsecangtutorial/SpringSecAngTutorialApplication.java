package com.codecool.springsecangtutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@SpringBootConfiguration
@EnableJpaRepositories("com.codecool.springsecangtutorial.DAO")
public class SpringSecAngTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecAngTutorialApplication.class, args);
        System.out.println("START");
    }
}
