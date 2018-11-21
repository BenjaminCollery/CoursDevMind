package com.esme.spring.faircorp.model;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LightDaoImpl implements LightDaoCustom {

    @PersistenceContext
    public EntityManager em;

    @Override
    public List<Light> findOnLights() {
        String jpql = "select lt from Light lt where lt.status = :value";
        return em.createQuery(jpql, Light.class)
                .setParameter("value", Status.ON)
                .getResultList();
    }

    public List<Long> findByRoom(Room room){
        String jpql = "select id from Light light where light.room= :value";
        return  em.createQuery(jpql, Long.class)
                .setParameter("value", room)
                .getResultList();
    }
}