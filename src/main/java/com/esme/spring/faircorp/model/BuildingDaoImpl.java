package com.esme.spring.faircorp.model;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class BuildingDaoImpl {
    @PersistenceContext // Permet de créer une EntityManager qui gère les entités persistantes
    public EntityManager em;

    /*
    Création d'une requête JPQL passée à l'EntityManager afin de sélectionner un building en fonction de son ID
     */

    public List<Building> findBuildingById(Long id){
        String jpql = "select building from Building building where building.id = :value";  // Déclaration et initiation de la chaîne de caractères qui contient la requête JPQL correspondante à notre volonté

        // Renvoi
        return (List<Building>) em.createQuery(jpql, Building.class) // Conversion de la requête JPQL en SQL en précisant la classe à laquelle la requête va s'appliquer, ici building
                .setParameter("value",id) // Ajout des paramètres à la requête, ici l'ID du building
                .getResultList(); // Récuération du résultat de la requête
    }


}