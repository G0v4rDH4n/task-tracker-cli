package com.upskill.tasktracker;

import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class CustomShellConfig {

    @Bean
    public PromptProvider promptProvider() {
        return () -> new AttributedString("tasks-cli>");
    }

}
