package com.esme.spring.faircorp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 	// Permet d'utiliser le mécanisme d'auto-configuration de Sprint Boot, d'enregister de nouveaux objets java dans le contexte de l'application Spring et d'importer des classes de configuration additionnelles
public class FaircorpApplication { 	// Création de la classe FaircorpApplication

	public static void main(String[] args) {
		SpringApplication.run(FaircorpApplication.class, args); 	// Permet d'exécuter l'application Spring Faircorp
	}
}
