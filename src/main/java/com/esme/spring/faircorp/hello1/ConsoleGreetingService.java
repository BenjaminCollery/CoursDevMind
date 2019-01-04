package com.esme.spring.faircorp.hello1;

import com.esme.spring.faircorp.hello1.GreetingService;
import org.springframework.stereotype.Service;

@Service // Annotation qui spécifie que la classe qui suit est un service
public class ConsoleGreetingService implements GreetingService {

    public void greet(String s){    // Méthode qui permet l'affichage de la chaîne de caractères s
        System.out.println(s);
    }   // Méthode qui permet d'affiche la chaine de caractères s

}
