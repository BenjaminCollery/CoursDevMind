package com.esme.spring.faircorp.model;

import javax.persistence.*;

// Déclaration de la classe Light

@Entity   // La classe sera persistante
public class Light {

    //Déclaration des attributs de la classe Light
    @Id     // La prochaine variable est un ID qui sert de clef primaire à la base de donnée
    @GeneratedValue(strategy= GenerationType.AUTO)  // Ajout automatique de la valeur de l'ID lors de l'insertion en base de données
    private Long id;

    // L'annotation Column introduit chaque attribut
    @Column(nullable=false)
    private Integer level;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING) // Déclare la valeur de l'attribut doit être convertie du type string de la base de données au type Status
    private Status status;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String brightness;


    @ManyToOne // Plusieurs lights peuvent appartenir à une seule room
    private Room room;

    // Constructeur vide
    public Light() {
    }

    // Constructeur avec les valeurs des attributs fournies en paramètres
    public Light(Integer level, Status status,Room room, String color , String brightness) {
        this.level = level;
        this.status = status;
        this.room = room;
        this.color = color;
        this.brightness = brightness;
    }

    // Déclaration des getters et des setters à tous les attributs des lights
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public String getColor() {return this.color;}

    public void setColor(String color) {this.color = color;}

    public String getBrightness() {return this.brightness;}

    public void setBrightness(String bri) {this.brightness = bri;}
}