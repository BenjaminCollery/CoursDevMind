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

@RunWith(SpringRunner.class)
public class DummyUserServiceTest {

    @Configuration // (2)
    @ComponentScan("com.esme.spring.faircorp.hello1")
    public static class DummmyUserServiceTestConfig{
    }


    @Autowired
    public DummyUserService dummyUserService;


    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void testGreetingAll() {
        dummyUserService.greetAll();
        outputCapture.expect(Matchers.stringContainsInOrder(
                Arrays.asList("Benjamin","Hugues")));
    }

}
