package com.esme.spring.faircorp.model;

public class RoomDto {

    private Long id;
    private String name;
    private Integer floor;
    private Long buildingId;
    private Status status;

    public RoomDto(){

    }




    public RoomDto(Room room){
        this.id = room.getId();
        this.name = room.getName();
        this.floor = room.getFloor();
        this.buildingId = room.getBuilding().getId();  // probleme car j'avais des room sans building
        this.status = room.getStatus();
    }

    public Long getId(){return id;}

    public String getName(){return name;}

    public Integer getFloor(){return floor;}


    public Status getStatus(){ return status;}

    public Long getBuildingId(){ return buildingId;}

}
