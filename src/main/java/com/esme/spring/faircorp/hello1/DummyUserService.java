package com.esme.spring.faircorp.hello1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DummyUserService implements UserService{


    @Autowired
    public DummyUserService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }


    private GreetingService greetingService;


    public void greetAll(){

        Arrays.asList("Benjamin","Hugues").forEach(name -> greetingService.greet(name));
    }
}
