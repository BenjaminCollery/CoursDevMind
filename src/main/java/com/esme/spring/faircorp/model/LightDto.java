package com.esme.spring.faircorp.model;

public class LightDto {

    private  Long id;
    private  Integer level;
    private  Status status;
    private  Long roomId;
    private String color;
    private String brightness;

    public LightDto() {

    }

    public LightDto(Light light) {
        this.id = light.getId();
        this.level = light.getLevel();
        this.status = light.getStatus();
        this.roomId = light.getRoom().getId();
        this.color = light.getColor();
        this.brightness = light.getBrightness();
    }

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

