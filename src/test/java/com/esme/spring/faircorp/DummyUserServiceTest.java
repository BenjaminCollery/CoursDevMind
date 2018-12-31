package com.esme.spring.faircorp;

import com.esme.spring.faircorp.hello1.DummyUserService;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)    // Indique à JUnit de faire appel à SpringRunner pour exécuter les tests
public class DummyUserServiceTest {

    @Configuration // Indique qu'une classe déclare des méthodes @Bean, qu'elle peut être traitée par le conteneur Spring pour générer des définitions de beans et des demandes de service pour ces beans lors de l'exécution
    @ComponentScan("com.esme.spring.faircorp.hello1")   // Permet de clairement identifier un composant Spring afin qu'il soit scanné et reconnu lors du build
    public static class DummmyUserServiceTestConfig{    // Définition d'une classe interne
    }


    @Autowired  // L'annotation Autowired signifie que la variable déclarée est un DAO
    public DummyUserService dummyUserService;


    @Rule   // Permet d'ajouter un règle
    public OutputCapture outputCapture = new OutputCapture();   // Permet de capturer les résultats des System.out et des System.err

    @Test   // Utilisation d'un Unit Test
    public void testGreetingAll() {
        dummyUserService.greetAll();    // Utilisation de dummyUserService.greetAll()
        outputCapture.expect(Matchers.stringContainsInOrder(Arrays.asList("Benjamin","Hugues"))); // Capture et test du résultat
    }

}
