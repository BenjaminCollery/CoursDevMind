package com.esme.spring.faircorp.model;

import javax.persistence.*;
import java.util.Set;

@Entity // (1)
public class Room {

    @Id// (2)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=255)// (3)
    private String name;

    @Column(nullable=false)
    private Integer floor;

    @Enumerated(EnumType.STRING)// (4)
    private Status status;

    @OneToMany(mappedBy="room")
    private Set<Light> lights;

    @ManyToOne
    private Building building;


    public Room() {
    }

    public Room(Integer floor, Status status, String name, Building building) {
        this.floor = floor;
        this.status = status;
        this.name = name;
        this.building = building;
    }

    public Room(Integer floor, String name) {
        this.floor = floor;
        this.name = name;
    }

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