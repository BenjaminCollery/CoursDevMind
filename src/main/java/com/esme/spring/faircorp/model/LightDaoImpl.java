package com.esme.spring.faircorp.model;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class LightDaoImpl implements LightDaoCustom { // Implémente l'interface LightDaoCustom

    @PersistenceContext // Permet de créer une EntityManager qui gère les entités persistantes
    public EntityManager em;

    /*
    Nouvelles définitions des méthodes de l'interface
    Création d'une requête JPQL passée à l'EntityManager afin de sélectionner une light en fonction de son statut
     */
    @Override // Signale et vérifie que la méthode est issue d'un héritage
    public List<Light> findOnLights() {
        String jpql = "select lt from Light lt where lt.status = :value";   // Déclaration et initiation de la chaîne de caractères qui contient la requête JPQL correspondante à notre volonté

        // Renvoi
        return em.createQuery(jpql, Light.class)    // Conversion de la requête JPQL en SQL en précisant la classe à laquelle la requête va s'appliquer, ici light
                .setParameter("value", Status.ON)   // Ajout des paramètres à la requête, ici le statut de la light
                .getResultList();   // Récuération du résultat de la requête
    }

    /*
   Création d'une requête JPQL passée à l'EntityManager afin de sélectionner une light en fonction de la chambre à laquelle elle appartient
    */
    public List<Long> findByRoom(Room room){
        String jpql = "select id from Light light where light.room= :value";    // Déclaration et initiation de la chaîne de caractères qui contient la requête JPQL correspondante à notre volonté

        // Renvoi
        return  em.createQuery(jpql, Long.class)    // Conversion de la requête JPQL en SQL en précisant la classe à laquelle la requête va s'appliquer, ici Long
                .setParameter("value", room)    // Ajout des paramètres à la requête, ici l'ID de la room à laquelle appartient la light
                .getResultList();   // Récuération du résultat de la requête
    }
}