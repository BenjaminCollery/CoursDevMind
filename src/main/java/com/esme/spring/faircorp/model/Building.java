package com.esme.spring.faircorp.model;

import javax.persistence.*;
import java.util.Set;

@Entity // (1)
public class Building {


    @Id// (2)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable=false, length=255)// (3)
    private String name;

    @OneToMany(mappedBy="building")
    private Set<Room> rooms;


    public Building() {
    }
    public Building(String name) {
        this.name = name;
    }



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