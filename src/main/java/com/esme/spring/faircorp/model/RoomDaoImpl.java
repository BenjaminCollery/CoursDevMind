package com.esme.spring.faircorp.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;
import java.util.List;

public class RoomDaoImpl implements RoomDaoCustom{  // Implémente l'interface RoomDaoCustom

    @PersistenceContext     // Permet de créer une EntityManager qui gère les entités persistantes
    public EntityManager em;

    /*
   Nouvelles définitions des méthodes de l'interface
   Création d'une requête JPQL passée à l'EntityManager afin de sélectionner une room en fonction de son nom
    */
    @Override   // Signale et vérifie que la méthode est issue d'un héritage
    public Room findRoomByName(String name) {
        String jpql = "select room from Room room where room.name = :value";    // Déclaration et initiation de la chaîne de caractères qui contient la requête JPQL correspondante à notre volonté

        // Renvoi
        return (Room) em.createQuery(jpql, Room.class)  // Conversion de la requête JPQL en SQL en précisant la classe à laquelle la requête va s'appliquer, ici room
                .setParameter("value", name)    // Ajout des paramètres à la requête, ici le nom de la room
                .getResultList();    // Récuération du résultat de la requête
    }


    /*
   Création d'une requête JPQL passée à l'EntityManager afin de sélectionner une room en fonction du building auquel elle appartient
    */
    public List<Long> findByBuilding(Building building){
        String jpql = "select id from Room room where room.building= :value";   // Déclaration et initiation de la chaîne de caractères qui contient la requête JPQL correspondante à notre volonté

        // Renvoi
        return  em.createQuery(jpql, Long.class)    // Conversion de la requête JPQL en SQL en précisant la classe à laquelle la requête va s'appliquer, ici Long
                .setParameter("value", building)    // Ajout des paramètres à la requête, ici l'ID du building auquel appartient la room
                .getResultList();    // Récuération du résultat de la requête
    }

    /*
   Création d'une requête JPQL passée à l'EntityManager afin de sélectionner une room en fonction de son ID
    */
    public List<Room> findRoomById(Long id){
        String jpql = "select room from Room room where room.id = :value";// Déclaration et initiation de la chaîne de caractères qui contient la requête JPQL correspondante à notre volonté

        // Renvoi
        return (List<Room>) em.createQuery(jpql, Room.class)    // Conversion de la requête JPQL en SQL en précisant la classe à laquelle la requête va s'appliquer, ici room
                .setParameter("value",id)   // Ajout des paramètres à la requête, ici l'ID de la room
                .getResultList();    // Récuération du résultat de la requête
    }
}
