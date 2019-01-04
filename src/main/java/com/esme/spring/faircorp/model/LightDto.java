package com.esme.spring.faircorp.model;

/*
Création d'un objet de transfert de données pour la classe Building
 */
public class LightDto {

    // Déclaration des attributs d'une light
    private  Long id;
    private  Integer level;
    private  Status status;
    private  Long roomId;
    private String color;
    private String brightness;

    // Constructeur vide
    public LightDto() {
    }

    // Constructeur du Dto d'une light fournie en paramètres
    public LightDto(Light light) {
        this.id = light.getId();
        this.level = light.getLevel();
        this.status = light.getStatus();
        this.roomId = light.getRoom().getId();
        this.color = light.getColor();
        this.brightness = light.getBrightness();
    }

    // Définition des getters du dto
    public Long getId() {
        return id;
    }

    public Integer getLevel() {
        return level;
    }

    public Status getStatus() {
        return status;
    }

    public Long getRoomId(){
        return roomId;
    }

    public String getColor() {return color;}

    public String getBrightness() {return brightness;}
}

