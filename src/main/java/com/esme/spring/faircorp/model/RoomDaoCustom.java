package com.esme.spring.faircorp.model;

import java.util.List;

public interface RoomDaoCustom {
    Room findRoomByName(String name);
    List<Long> findByBuilding(Building id);

}
