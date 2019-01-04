package com.esme.spring.faircorp.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDao extends JpaRepository<Room, Long> {    // Crée un DAO capable d'avoir recours à toutes les méthodes implémentées dans le JpaRepository - interface supplémentaire de Spring Data JPA
}
