package com.example.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class GameApplication {

    public static void main(String[] args) {SpringApplication.run(GameApplication.class, args);

    }

}
