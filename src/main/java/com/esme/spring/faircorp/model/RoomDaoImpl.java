package com.esme.spring.faircorp.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;
import java.util.List;

public class RoomDaoImpl implements RoomDaoCustom{

    @PersistenceContext
    public EntityManager em;

    @Override
    public Room findRoomByName(String name) {
        String jpql = "select room from Room room where room.name = :value";
        return (Room) em.createQuery(jpql, Room.class)
                .setParameter("value", name)
                .getResultList();
    }


    public List<Long> findByBuilding(Building building){
        String jpql = "select id from Room room where room.building= :value";
        return  em.createQuery(jpql, Long.class)
                .setParameter("value", building)
                .getResultList();
    }

    public List<Room> findRoomById(Long id){
        String jpql = "select room from Room room where room.id = :value";
        return (List<Room>) em.createQuery(jpql, Room.class)
                .setParameter("value",id)
                .getResultList();
    }
}
