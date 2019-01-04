package com.esme.spring.faircorp.hello1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController     // Annonce l'utilisation d'un service REST - controlleur
@RequestMapping("/api/hello")    // Permet de définir le préfixe de l'URL qui mène à notre base de données
@Transactional   // Classe utilisée lors des transactions
public class HelloController {


    @GetMapping("/{name}")   // La méthode suivante répond à une requête de type GET lorsqu'on lui précise un nom
    public MessageDto welcome(@PathVariable String name) {
        return new MessageDto("Hello " + name);     // Renvoi un message contenant le nom fourni
    }


    // définition d'une classe interne MessageDto
    class MessageDto {
        String message;     // Déclaration d'une chaîne de caractères

        public MessageDto(String message) {
            this.message = message;
        }   // Initialisation de la classe interne

        public String getMessage() {
            return message;
        }    // Définition d'un getter de la classe interne
    }
}