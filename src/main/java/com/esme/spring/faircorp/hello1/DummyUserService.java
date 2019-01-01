package com.esme.spring.faircorp.hello1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service    // Annotation qui spécifie que la classe qui suit est un service
public class DummyUserService implements UserService{


    @Autowired     // L'annotation Autowired signifie que la variable déclarée est un DAO
    public DummyUserService(GreetingService greetingService) {   // Constructeur avec un service fourni en argument
        this.greetingService = greetingService;
    }

    private GreetingService greetingService;    // Déclaration d'un service greetingService

    public void greetAll(){     // Méthode utilisant le service précédent

        Arrays.asList("Benjamin","Hugues").forEach(name -> greetingService.greet(name)); // Pour chaque élément de la liste, appliquer la méthode greet du service greetingService
    }
}
