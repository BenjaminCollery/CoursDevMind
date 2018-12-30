package com.esme.spring.faircorp.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
Création d'un objet de transfert de données pour la classe Building
 */
public class BuildingDto {

    // Déclaration des attributs d'un building
    private Long id;
    private String name;

    // Constructeur vide
    public BuildingDto(){
    }

    // Constructeur du Dto d'un building fourni en paramètres
    public BuildingDto(Building building){
        this.id = building.getId();
        this.name = building.getName();
    }

    // Définition des getteurs du dto
    public Long getId(){return id;}

    public String getName(){return name;}

}
