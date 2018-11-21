package com.esme.spring.faircorp.model;

import java.util.List;

public interface LightDaoCustom {
    List<Light> findOnLights();
    List<Long> findByRoom(Room room);

}
