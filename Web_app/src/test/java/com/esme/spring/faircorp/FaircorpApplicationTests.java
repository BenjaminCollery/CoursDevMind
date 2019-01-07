package com.esme.spring.faircorp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 	// Indique à JUnit de faire appel à SpringRunner pour exécuter les tests
@SpringBootTest 	// Fournit des fonctionnalités supplémentaires
public class FaircorpApplicationTests {

	@Test 	// Utilisation d'un Unit Test
	public void contextLoads() { 	// Utilisation du SpringBootContextLoader grâce à l'annotation précédente SpringBootTest
	}

}
