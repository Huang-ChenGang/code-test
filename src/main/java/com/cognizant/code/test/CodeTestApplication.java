package com.cognizant.code.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CodeTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeTestApplication.class, args);
    }
}
