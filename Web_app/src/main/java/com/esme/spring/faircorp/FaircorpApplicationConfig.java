package com.esme.spring.faircorp;

import com.esme.spring.faircorp.hello1.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Indique qu'une classe déclare des méthodes @Bean, qu'elle peut être traitée par le conteneur Spring pour générer des définitions de beans et des demandes de service pour ces beans lors de l'exécution
public class FaircorpApplicationConfig {

    @Autowired  // L'annotation Autowired signifie que la variable déclarée est un DAO
    private GreetingService greetingService;

    @Bean   // Déclaration d'un bean - objet java
    public CommandLineRunner greetingCommandLine() {

        greetingService.greet("coucou");    // Utilisation du service greetingService

        // Renvoi
        return new CommandLineRunner() {
            @Override   // Signale et vérifie que la méthode est issue d'un héritage
            public void run(String... args) throws Exception {  // Avertissement qu'une exception est possible
            }
        };
    }
}