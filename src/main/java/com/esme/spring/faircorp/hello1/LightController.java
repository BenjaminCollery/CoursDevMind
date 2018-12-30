package com.esme.spring.faircorp.hello1;

import com.esme.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin    // Permet d'ouvrir l'API avec plusieurs applications différentes
@RestController    // Annonce l'utilisation d'un service REST - controlleur
@RequestMapping("/api/lights")  // Permet de définir le préfixe de l'URL qui mène à notre base de données
@Transactional      // Classe utilisée lors des transactions
public class LightController {

    public SimpleMqttClient simpleMqttClient;   // Déclaration d'un client MQTT

    // L'annotation Autowired signifie que la variable déclarée est un DAO

    @Autowired
    private  LightDao lightDao;

    @Autowired
    private RoomDao roomDao;


    @GetMapping     // La méthode suivante répond à une requête de type GET
    public List<LightDto> findAll() {   // Méthode qui permet d'obtenir toutes les lights avec leurs attributs

        this.simpleMqttClient.connecting();     // Connection au client MQTT

        return lightDao.findAll()    // Sélectionne toutes les lights
                .stream()   // Convertit toutes les lights sélectionnées en stream
                .map(LightDto::new)  // Convertit tous les stream en lightDto
                .collect(Collectors.toList());  // Collecte les résultats de la conversion / le return initial renvoie le résultat de cette collecte
    }

    @GetMapping(path = "/{id}")     // La méthode suivante répond à une requête de type GET lorsqu'on lui précise l'ID d'une light
    public LightDto findById(@PathVariable Long id) {   // Méthode qui permet d'obtenir toutes les caractéristiques d'une light précise caractérisée par son ID
        return lightDao.findById(id).map(light -> new LightDto(light)).orElse(null);    // Sélectionne la light dont l'ID correspond - la convertit en lightDto puis le renvoie ou ne renvoie rien si l'ID est introuvable
    }

    @PutMapping(path = "/{id}/switch")   // La méthode suivante répond à une requête de type PUT lorsqu'on lui précise l'ID d'une light et l'action à effectuer
    public LightDto switchStatus(@PathVariable Long id) {   // Méthode qui permet de modifier le statut (ON/OFF) d'une light

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);     // On sélectionne la light désignée par l'ID ou on renvoie une erreur si l'ID ne désigne rien
        light.setStatus(light.getStatus() == Status.ON ? Status.OFF: Status.ON);    // On teste la valeur du statut puis on la modifie

        this.simpleMqttClient.publishMqtt(id.toString()+" switch " + light.getStatus().toString() , "order");    // Envoyer le get light status en message MQTT à arduino , qui selon le message allume ou éteint la light

        return new LightDto(light);     // Renvoi de la light modifiée
    }

    @PutMapping(path = "/{id}/switchcolor/{color}")     // La méthode suivante répond à une requête de type PUT lorsqu'on lui précise l'ID d'une light et l'action à effectuer
    public LightDto switchStatus(@PathVariable Long id , @PathVariable String color) {  // Méthode qui permet de modifier la couleur d'une light

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);     // On sélectionne la light désignée par l'ID ou on renvoie une erreur si l'ID ne désigne rien

        light.setColor(color);  // On modifie la couleur de la light en focntion de l'argument



        this.simpleMqttClient.publishMqtt(id.toString() + " changeColor " + light.getColor() , "order");    // Envoyer le get light color en message MQTT à arduino , qui selon le message modifie la couleur de la light

        return new LightDto(light);     // Renvoi de la light modifiée
    }

    @PutMapping(path = "/{id}/changeBrightness/{brightness}")   // La méthode suivante répond à une requête de type PUT lorsqu'on lui précise l'ID d'une light et l'action à effectuer
    public LightDto switchStatus(@PathVariable Long id , @PathVariable String color, @PathVariable String brightness) {     // Méthode qui permet de modifier la brightness d'une light

        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);     // On sélectionne la light désignée par l'ID ou on renvoie une erreur si l'ID ne désigne rien

        light.setBrightness(brightness);    // On ajuste la valeur de la brightness de la light en fonction de l'argument

        this.simpleMqttClient.publishMqtt(id.toString() + " changeBrightness " + light.getBrightness() , "order");    // Envoyer le get light brightness en message MQTT à arduino , qui selon le message ajuste la valeur de la brightness de la light

        return new LightDto(light);     // Renvoi de la light modifiée
    }

    @PostMapping    // La méthode suivante répond à une requête de type POST
    public LightDto create(@RequestBody LightDto dto) {     // Méthode qui permet de créer une nouvelle light - L'annotation permet de déserialiser automatiquement le JSON reçu en LightDto
        Light light = null; // Déclaration et initialisation d'une light

        /* On vérifie si l'ID fourni dans le JSON existe déjà
        S'il existe déjà, on sélectionne la light correspondante
        Sinon la light initialisée dans cette méthode conserve la valeur null
        */

        if (dto.getId() != null) {
            light = lightDao.findById(dto.getId()).orElse(null);
        }

        /* Si la light est toujours null / c'est-à-dire que son ID n'existait pas encore
        On construit une nouvelle light en récupérant le level, le statut, l'ID de la room, la couleur et la Brightness fournis dans le JSON
         */

        if (light == null) {
            light = lightDao.save(new Light(dto.getLevel(), dto.getStatus(), roomDao.getOne(dto.getRoomId()) , dto.getColor(), dto.getBrightness()));
        }

        /* Au contraire, si l'ID existait déjà
        On se contente de modifier les attributs de la light déjà existante
         */

        else {
            light.setLevel(dto.getLevel());
            light.setStatus(dto.getStatus());
            lightDao.save(light);
        }

        return new LightDto(light);     // Renvoi de la nouvelle light
    }

    @DeleteMapping(path = "/{id}")   // La méthode suivante répond à une requête de type DELETE lorsqu'on lui précise l'ID d'une light
    public void delete(@PathVariable Long id) {

        lightDao.deleteById(id);    // On supprime chaque light
    }
}