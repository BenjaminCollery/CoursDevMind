package com.esme.spring.faircorp.hello;

import com.esme.spring.faircorp.hello1.ConsoleGreetingService;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.test.rule.OutputCapture;

public class ConsoleGreetingServiceTests {

    @Rule   // Permet d'ajouter un règle
    public OutputCapture outputCapture = new OutputCapture();   // Permet de capturer les résultats des System.out et des System.err

    @Test // Utilisation d'un Unit Test
    public void testGreeting() {
        ConsoleGreetingService greetingService = new ConsoleGreetingService();  // Déclaration d'une ConsolegreetingService
        greetingService.greet("Hello, Spring!");    // utilisation du greetingService.greet()
        outputCapture.expect(Matchers.startsWith("Hello, Spring!"));    // Capture et test du résultat
    }

}
