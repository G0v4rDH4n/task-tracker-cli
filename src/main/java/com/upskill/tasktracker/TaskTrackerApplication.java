package com.upskill.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskTrackerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TaskTrackerApplication.class);
        app.setBanner(new CustomBannerConfig().customBanner());
        app.run(args);
    }

}
