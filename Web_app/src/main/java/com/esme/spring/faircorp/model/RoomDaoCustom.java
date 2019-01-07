package com.esme.spring.faircorp.model;

import java.util.List;

public interface RoomDaoCustom {     // Création d'une interface

    // Ajout de deux méthodes à l'interface
    Room findRoomByName(String name);
    List<Long> findByBuilding(Building id);

}
