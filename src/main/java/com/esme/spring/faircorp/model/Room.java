package com.esme.spring.faircorp.model;

import javax.persistence.*;
import java.util.Set;

// Déclaration de la classe Room

@Entity     // La classe sera persistante
public class Room {

    //Déclaration des attributs de la classe Room
    @Id     // La prochaine variable est un ID qui sert de clef primaire à la base de donnée
    @GeneratedValue(strategy= GenerationType.IDENTITY)  // Ajout automatique de la valeur de l'ID lors de l'insertion en base de données

  
    private Long id;

    // L'annotation Column introduit chaque attribut
    @Column(nullable=false, length=255)
    private String name;

    @Column(nullable=false)
    private Integer floor;

    @Enumerated(EnumType.STRING)    // Déclare la valeur de l'attribut doit être convertie du type string de la base de données au type Status
    private Status status;

    @OneToMany(mappedBy="room")     // Une room peut contenir plusieurs lights
    private Set<Light> lights;

    @ManyToOne  // Plusieurs rooms peuvent appartenir à un seul building
    private Building building;


    // Constructeur vide
    public Room() {
    }

    // Constructeur avec les valeurs des attributs fournies en paramètres
    public Room(Integer floor, Status status, String name, Building building) {
        this.floor = floor;
        this.status = status;
        this.name = name;
        this.building = building;
    }

    // Constructeur où seuls l'étage et le nom de la chambre sont fournis en paramètres
    public Room(Integer floor, String name) {
        this.floor = floor;
        this.name = name;
    }

    // Déclaration des getters et des setters à tous les attributs des lights
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return floor;
    }

    public void setLevel(Integer level) {
        this.floor = floor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setName(String name){ this.name = name;}

    public String getName(){ return name;}

    public void setFloor(Integer floor){ this.floor = floor;}

    public Integer getFloor(){ return floor;}

    public Building getBuilding(){return building;}
}