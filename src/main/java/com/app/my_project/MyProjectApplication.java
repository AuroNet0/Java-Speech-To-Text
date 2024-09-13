package com.app.my_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyProjectApplication {

    private static final Logger logger = LoggerFactory.getLogger(MyProjectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyProjectApplication.class, args);

        logger.info("APP INICIADA");
    }


}
