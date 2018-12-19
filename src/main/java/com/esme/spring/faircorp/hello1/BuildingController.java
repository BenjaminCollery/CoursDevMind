package com.esme.spring.faircorp.hello1;

import com.esme.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/buildings")
@Transactional
public class BuildingController {

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

    @GetMapping
    public List<BuildingDto> findAll(){


        return buildingDao.findAll()
                .stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{building_id}")
    public BuildingDto findById(@PathVariable Long building_id){
        return buildingDao.findById(building_id).map(building -> new BuildingDto(building)).orElse(null);
    }

    @PostMapping
    public BuildingDto create(@RequestBody BuildingDto dto){
        Building building = null;

        if (dto.getId() != null){
            building = buildingDao.findById(dto.getId()).orElse(null);
        }

        if (building == null){
            building = buildingDao.save(new Building((dto.getName())));
        }
        else {
            building.setName(dto.getName());
            buildingDao.save(building);
        }

        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{building_id}")
    public void delete(@PathVariable Long building_id){

        List<Building> building = buildingDaoImpl.findBuildingById(building_id);    // building est ici une List avec 1 building

        List<Long> listRoomId  = roomDaoCustom.findByBuilding(building.get(0));  // on recupere le seul contenu dans building

        Room room = null;

        for (Long roomId: listRoomId) {


            room = roomDao.findById(roomId).orElse(null);     // on recupere chaque room grace a son id

            List<Long> listLightId = lightDaoCustom.findByRoom(room);    // on recupere la liste des id des light de cette room

            for(Long lightId: listLightId){

                if (lightId != null) {
                    lightDao.deleteById(lightId);    // test sinon j'ai des pointeurs null
                }
            }

            roomDao.deleteById(roomId);
        }


        buildingDao.deleteById(building_id);


    }
}
