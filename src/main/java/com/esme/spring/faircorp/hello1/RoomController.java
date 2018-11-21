package com.esme.spring.faircorp.hello1;


import com.esme.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private LightDao lightDao;
    @Autowired RoomDaoImpl roomDaoImpl;
    @Autowired LightDaoCustom lightDaoCustom;


    @GetMapping
    public List<RoomDto> findAll(){
        return roomDao.findAll()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{room_id}")
    public RoomDto finById(@PathVariable Long room_id){
        return roomDao.findById(room_id).map(room -> new RoomDto(room)).orElse(null);
    }

    // @PutMapping(path = "/{room_id}/switchLight")  //permet de changer l'état des light
   // public


    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto){
       Room room = null;

       if (dto.getId() != null){
           room = roomDao.findById(dto.getId()).orElse(null);
       }

       if (room == null){
           room = roomDao.save(new Room(dto.getFloor() , dto.getStatus(), dto.getName(), buildingDao.getOne(dto.getBuildingId())));
       }

       else{
           room.setFloor(dto.getFloor());
           room.setStatus(dto.getStatus());
           roomDao.save(room);
       }

       return new RoomDto(room);

    }

    @DeleteMapping(path = "/{room_id}")
    public void delete(@PathVariable Long room_id){

        List<Room> room = roomDaoImpl.findRoomById(room_id);

        List<Long> listLightId  = lightDaoCustom.findByRoom(room.get(0));


        for (Long lightId: listLightId) {

            lightDao.deleteById(lightId);
        }


        roomDao.deleteById(room_id);



    }



}
