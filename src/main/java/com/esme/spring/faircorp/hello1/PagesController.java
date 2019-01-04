package com.esme.spring.faircorp.hello1;

import com.esme.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin    // Permet d'ouvrir l'API avec plusieurs applications différentes
@Controller     // Annonce l'utilisation d'un controlleur - ici le controlleur des différentes pages générées pour créer des buildings, des rooms ou des lights
public class PagesController {


    @GetMapping("/create_building")     // La méthode suivante répond à une requête de type GET lorsqu'on lui précise qu'on veut créer un building
    public String createBuilding(   // Permet de créer la page create_building
        @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {    // @RequestParam est une annotation qui permet d'accéder aux valeurs du paramètre de requête à partir de la demande
        model.addAttribute("name", name);   // "name" est un nom utilise dans notre vue pour obtenir la valeur de name
        return "create_building";   // On renvoie le modèle déjà traité par src/main/resources/templates/create_building.html
    }

    @GetMapping("/create_room")     // La méthode suivante répond à une requête de type GET lorsqu'on lui précise qu'on veut créer une room
    public String createRoom(   // Permet de créer la page create_room
            @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {    // @RequestParam est une annotation qui permet d'accéder aux valeurs du paramètre de requête à partir de la demande
        model.addAttribute("name", name);   // "name" est un nom utilise dans notre vue pour obtenir la valeur de name
        return "create_room";   // On renvoie le modèle déjà traité par src/main/resources/templates/create_room.html
    }

    @GetMapping("/create_light")    // La méthode suivante répond à une requête de type GET lorsqu'on lui précise qu'on veut créer une light
    public String createLight(  // Permet de créer la page create_light
            @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {    // @RequestParam est une annotation qui permet d'accéder aux valeurs du paramètre de requête à partir de la demande.
        model.addAttribute("name", name);   // "name" est un nom utilise dans notre vue pour obtenir la valeur de name
        return "create_light";  // On renvoie le modèle déjà traité par src/main/resources/templates/create_light.html
    }

}
