package com.esme.spring.faircorp.model;

import javax.persistence.*;
import java.util.Set;

// Déclaration de la classe Building

@Entity // La classe sera persistante
public class Building {

    //Déclaration des attributs de la classe building
    @Id     // La prochaine variable est un ID qui sert de clef primaire à la base de donnée
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Ajout automatique de la valeur de l'ID lors de l'insertion en base de données
    private Long id;

    @Column(nullable=false, length=255) // L'annotation Column introduit chaque propriété
    private String name;

    @OneToMany(mappedBy="building") // Un building peut abriter plusieurs chambres
    private Set<Room> rooms;


    // Constructeur vide
    public Building() {
    }

    // Constructeur à partir du nom
    public Building(String name) {
        this.name = name;
    }

    //Ensemble des getters et setters permettant de lire et de modifier l'ID et le nom du building

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}