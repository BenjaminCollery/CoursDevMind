package com.esme.spring.faircorp.model;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class BuildingDaoImpl {
    @PersistenceContext
    public EntityManager em;

    public List<Building> findBuildingById(Long id){
        String jpql = "select building from Building building where building.id = :value";
        return (List<Building>) em.createQuery(jpql, Building.class)
                .setParameter("value",id)
                .getResultList();
    }


}