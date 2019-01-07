package com.esme.spring.faircorp;

import com.esme.spring.faircorp.model.LightDaoCustom;
import com.esme.spring.faircorp.model.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@RunWith(SpringRunner.class)    // Indique à JUnit de faire appel à SpringRunner pour exécuter les tests
@DataJpaTest    // Focalise le test sur les composants JPA
@ComponentScan  // Permet de clairement identifier un composant Spring afin qu'il soit scanné et reconnu lors du build


public class LightDaoImplTest {

    @Autowired  // L'annotation Autowired signifie que la variable déclarée est un DAO
    LightDaoCustom lightDaoCustom;

    @Test   // Utilisation d'un Unit Test
    public void shouldFindOnLights() {
                assertThat(lightDaoCustom.findOnLights())   // Vérifie les caractéristiques du résultat de FindOnLights()
                .hasSize(1)
                .extracting("id", "status")
                .containsExactly(tuple(-1L, Status.ON));
    }
}