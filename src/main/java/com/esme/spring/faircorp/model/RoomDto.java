package com.esme.spring.faircorp.model;

/*
Création d'un objet de transfert de données pour la classe room
 */
public class RoomDto {

    // Déclaration des attributs d'une room
    private Long id;
    private String name;
    private Integer floor;
    private Long buildingId;
    private Status status;

    // Constructeur vide
    public RoomDto(){
    }

    // Constructeur du Dto d'une room fournie en paramètres
    public RoomDto(Room room){
        this.id = room.getId();
        this.name = room.getName();
        this.floor = room.getFloor();
        this.buildingId = room.getBuilding().getId();  // probleme car j'avais des room sans building
        this.status = room.getStatus();
    }

    // Définition des getters du dto
    public Long getId(){return id;}

    public String getName(){return name;}

    public Integer getFloor(){return floor;}

    public Status getStatus(){ return status;}

    public Long getBuildingId(){ return buildingId;}

}
