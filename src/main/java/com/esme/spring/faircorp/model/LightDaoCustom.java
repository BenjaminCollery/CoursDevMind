package com.esme.spring.faircorp.model;

import java.util.List;

public interface LightDaoCustom {   // Création d'une interface

    // Ajout de deux méthodes à l'interface
    List<Light> findOnLights();
    List<Long> findByRoom(Room room);

}
