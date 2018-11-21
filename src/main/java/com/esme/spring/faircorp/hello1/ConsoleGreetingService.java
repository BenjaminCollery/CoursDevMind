package com.esme.spring.faircorp.hello1;

import com.esme.spring.faircorp.hello1.GreetingService;
import org.springframework.stereotype.Service;

@Service
public class ConsoleGreetingService implements GreetingService {

    public void greet(String s){
        System.out.println(s);
    }

}
