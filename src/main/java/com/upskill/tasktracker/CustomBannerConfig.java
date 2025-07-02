package com.upskill.tasktracker;

import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBannerConfig {

    @Bean
    public Banner customBanner() {
        return (environment, sourceClass, out) -> {
            out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            out.println("||                                                       ||");
            out.println("||               Task Tracker CLI App v1                 ||");
            out.println("||       * Add your daily tasks and activities *         ||");
            out.println("||                                                       ||");
            out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n");
        };
    }
}

