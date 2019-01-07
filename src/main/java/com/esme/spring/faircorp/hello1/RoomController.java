package com.esme.spring.faircorp.hello1;


import com.esme.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin     // Permet d'ouvrir l'API avec plusieurs applications différentes
@RestController     // Annonce l'utilisation d'un service REST - controlleur
@RequestMapping("/api/rooms")   // Permet de définir le préfixe de l'URL qui mène à notre base de données
@Transactional      // Classe utilisée lors des transactions
public class RoomController {

    // L'annotation Autowired signifie que la variable déclarée est un DAO

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private LightDao lightDao;
    @Autowired RoomDaoImpl roomDaoImpl;
    @Autowired LightDaoCustom lightDaoCustom;


    @GetMapping     // La méthode suivante répond à une requête de type GET
    public List<RoomDto> findAll(){     // Méthode qui permet d'obtenir toutes les rooms avec leurs attributs
        return roomDao.findAll()    // Sélectionne toutes les rooms
                .stream()   // Convertit toutes les rooms sélectionnées en stream
                .map(RoomDto::new)  // Convertit tous les stream en roomDto
                .collect(Collectors.toList());  // Collecte les résultats de la conversion / le return initial renvoie le résultat de cette collecte
    }

    @GetMapping(path = "/{room_id}")    // La méthode suivante répond à une requête de type GET lorsqu'on lui précise l'ID d'une room
    public RoomDto finById(@PathVariable Long room_id){     // Méthode qui permet d'obtenir toutes les caractéristiques d'une room précise caractérisée par son ID
        return roomDao.findById(room_id).map(room -> new RoomDto(room)).orElse(null);   // Sélectionne la room dont l'ID correspond - la convertit en roomDto puis le renvoie ou ne renvoie rien si l'ID est introuvable
    }

    @PostMapping    // La méthode suivante répond à une requête de type POST
    public RoomDto create(@RequestBody RoomDto dto){    // Méthode qui permet de créer une nouvelle room - L'annotation permet de déserialiser automatiquement le JSON reçu en RoomDto
       Room room = null;    // Déclaration et initialisation d'une room

        /* On vérifie si l'ID fourni dans le JSON existe déjà
        S'il existe déjà, on sélectionne la room correspondante
        Sinon la room initialisée dans cette méthode conserve la valeur null
        */

       if (dto.getId() != null){
           room = roomDao.findById(dto.getId()).orElse(null);
       }

       /* Si la room est toujours null / c'est-à-dire que son ID n'existait pas encore
        On construit une nouvelle room en récupérant l'étage, le statut, le nom et l'ID du building fournis dans le JSON
         */

       if (room == null){
           room = roomDao.save(new Room(dto.getFloor() , dto.getStatus(), dto.getName(), buildingDao.getOne(dto.getBuildingId())));
       }

       /* Au contraire, si l'ID existait déjà
        On se contente de modifier les attributs de la room déjà existante
         */

       else{
           room.setFloor(dto.getFloor());
           room.setStatus(dto.getStatus());
           roomDao.save(room);
       }

       return new RoomDto(room);    // Renvoi de la nouvelle room

    }

    @DeleteMapping(path = "/{room_id}")     // La méthode suivante répond à une requête de type DELETE lorsqu'on lui précise l'ID d'une room
    public void delete(@PathVariable Long room_id){

        List<Room> room = roomDaoImpl.findRoomById(room_id);    // On sélectionne la room concernée par l'ID passé en argument

        List<Long> listLightId  = lightDaoCustom.findByRoom(room.get(0));   // On récupère la liste des ID des lights de la room


        for (Long lightId: listLightId) {   // On parcourt l'ensemble des lights

            lightDao.deleteById(lightId);    // On supprime la light
        }


        roomDao.deleteById(room_id);    // On supprime la room



    }



}
