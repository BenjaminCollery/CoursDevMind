package com.esme.spring.faircorp.hello1;

import com.esme.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin    // Permet d'ouvrir l'API avec plusieurs applications différentes
@RestController     // Annonce l'utilisation d'un service REST - ici controlleur de la classe building
@RequestMapping("api/buildings")    // Permet de définir le préfixe de l'URL qui mène à notre base de données
@Transactional  // Classe utilisée lors des transactions
public class BuildingController {

    // L'annotation Autowired signifie que la variable déclarée est un DAO

    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private LightDao lightDao;

    @Autowired RoomDaoCustom roomDaoCustom;
    @Autowired LightDaoCustom lightDaoCustom;
    @Autowired BuildingDaoImpl buildingDaoImpl;
    @Autowired LightDaoImpl lightDaoImpl;


    @GetMapping     // La méthode suivante répond à une requête de type GET
    public List<BuildingDto> findAll(){     // Méthode qui permet d'obtenir tous les buildings avec leurs attributs

        return buildingDao.findAll()    // Sélectionne tous les buildings
                .stream()   // Convertit tous les buildings sélectionnés en stream
                .map(BuildingDto::new)  // Convertit tous les stream en buildingDto
                .collect(Collectors.toList());  // Collecte les résultats de la conversion / le return initial renvoie le résultat de cette collecte
    }

    @GetMapping(path = "/{building_id}")    // La méthode suivante répond à une requête de type GET lorsqu'on lui précise l'ID d'un building
    public BuildingDto findById(@PathVariable Long building_id){    // Méthode qui permet d'obtenir toutes les caractéristiques d'un building précis caractérisé par son ID
        return buildingDao.findById(building_id).map(building -> new BuildingDto(building)).orElse(null); // Sélectionne le building dont l'ID correspond - le convertit en BuildingDto puis le renvoie ou ne renvoie rien si l'ID est introuvable
    }

    @PostMapping    // La méthode suivante répond à une requête de type POST
    public BuildingDto create(@RequestBody BuildingDto dto){    // Méthode qui permet de créer un nouveau building - L'annotation permet de déserialiser automatiquement le JSON reçu en BuildingDto
        Building building = null; // Déclaration et initialisation d'un building

        /* On vérifie si l'ID fourni dans le JSON existe déjà
        S'il existe déjà, on sélectionne le building correspondant
        Sinon le building initialisé dans cette méthode conserve la valeur null
        */

        if (dto.getId() != null){
            building = buildingDao.findById(dto.getId()).orElse(null);
        }

        /* Si le building est toujours null / c'est-à-dire que son ID n'existait pas encore
        On construit un nouveau Building en récupérant le nom fourni dans le JSON
         */

        if (building == null){
            building = buildingDao.save(new Building((dto.getName())));
        }

        /* Au contraire, si l'ID existait déjà
        On se contente de modifier le nom du building déjà existant
         */

        else {
            building.setName(dto.getName());
            buildingDao.save(building);
        }

        return new BuildingDto(building); // Renvoi du nouveau building
    }

    @DeleteMapping(path = "/{building_id}") // La méthode suivante répond à une requête de type DELETE lorsqu'on lui précise l'ID d'un building
    public void delete(@PathVariable Long building_id){

        List<Building> building = buildingDaoImpl.findBuildingById(building_id);    // building est ici une List avec 1 building correspondant à l'ID fourni

        List<Long> listRoomId  = roomDaoCustom.findByBuilding(building.get(0));  // on récupère la liste des ID des rooms du Building

        Room room = null; // On initialise une room à null

        for (Long roomId: listRoomId) {


            room = roomDao.findById(roomId).orElse(null);     // on récupère chaque room grace a son id

            List<Long> listLightId = lightDaoCustom.findByRoom(room);    // on récupère la liste des id des lights de cette room

            for(Long lightId: listLightId){

                if (lightId != null) {  // On vérifie que la light existe
                    lightDao.deleteById(lightId);    // On supprime chaque light de la room
                }
            }

            roomDao.deleteById(roomId);     // On supprime la room
        }


        buildingDao.deleteById(building_id);    // On peut maintenant supprimer le building


    }
}
