package com.esme.spring.faircorp.model;

import javax.persistence.*;

@Entity // (1)
public class Light {

    @Id// (2)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)// (3)
    private Integer level;

    @Column(nullable=false)// (4)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String brightness;


    @ManyToOne
    private Room room;

    public Light() {
    }

    public Light(Integer level, Status status,Room room, String color , String brightness) {
        this.level = level;
        this.status = status;
        this.room = room;
        this.color = color;
        this.brightness = brightness;
    }

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