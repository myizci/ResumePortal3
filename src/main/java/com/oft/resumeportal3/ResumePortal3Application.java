package com.oft.resumeportal3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ResumePortal3Application {

    public static void main(String[] args) {
        SpringApplication.run(ResumePortal3Application.class, args);
    }

}
