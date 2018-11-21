package com.esme.spring.faircorp;

import com.esme.spring.faircorp.hello1.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// (1)
@Configuration
public class FaircorpApplicationConfig {

    @Autowired
    private GreetingService greetingService;

    @Bean
    public CommandLineRunner greetingCommandLine() { // (3)

        greetingService.greet("coucou");

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // (4)
            }
        };
    }
}