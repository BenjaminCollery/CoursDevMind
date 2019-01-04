package com.esme.spring.faircorp.hello1;

import com.esme.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
public class PagesController {


    @GetMapping("/create_building")
    public String createBuilding(
        @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "create_building";
        // returns the already proccessed model from src/main/resources/templates/greeting.html
    }

    @GetMapping("/create_room")
    public String createRoom(
            @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "create_room";
        // returns the already proccessed model from src/main/resources/templates/greeting.html
    }

    @GetMapping("/create_light")
    public String createLight(
            @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "create_light";
        // returns the already proccessed model from src/main/resources/templates/greeting.html
    }

}
