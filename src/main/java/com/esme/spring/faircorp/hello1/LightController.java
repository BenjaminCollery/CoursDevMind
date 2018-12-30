package com.esme.spring.faircorp.hello1;

import com.esme.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController  // (1)
@RequestMapping("/api/lights") // (2)
@Transactional // (3)
public class LightController {

    public SimpleMqttClient simpleMqttClient;
    @Autowired
    private  LightDao lightDao; // (4)
    @Autowired
    private RoomDao roomDao;


    @GetMapping // (5)
    public List<LightDto> findAll() {

        this.simpleMqttClient.connecting();

        return lightDao.findAll()
                .stream()
                .map(LightDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Long id) {
        return lightDao.findById(id).map(light -> new LightDto(light)).orElse(null);
    }

    @PutMapping(path = "/{id}/switch")
    public LightDto switchStatus(@PathVariable Long id) {
        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);
        light.setStatus(light.getStatus() == Status.ON ? Status.OFF: Status.ON);

        this.simpleMqttClient.publishMqtt(id.toString()+" switch " + light.getStatus().toString() , "order");    // envoyer le get light status en message MQTT à arduino , qui selon le message allume ou éteind

        return new LightDto(light);
    }

    @PutMapping(path = "/{id}/switchcolor/{color}")
    public LightDto switchStatus(@PathVariable Long id , @PathVariable String color) {
        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        light.setColor(color);



        this.simpleMqttClient.publishMqtt(id.toString() + " changeColor " + light.getColor() , "order");    // envoyer le get light status en message MQTT à arduino , qui selon le message allume ou éteind

        return new LightDto(light);
    }

    @PutMapping(path = "/{id}/changeBrightness/{brightness}")
    public LightDto switchStatus(@PathVariable Long id , @PathVariable String color, @PathVariable String brightness) {
        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        light.setBrightness(brightness);

        this.simpleMqttClient.publishMqtt(id.toString() + " changeBrightness " + light.getBrightness() , "order");    // envoyer le get light status en message MQTT à arduino , qui selon le message allume ou éteind

        return new LightDto(light);
    }

    @PostMapping
    public LightDto create(@RequestBody LightDto dto) {
        Light light = null;

        if (dto.getId() != null) {
            light = lightDao.findById(dto.getId()).orElse(null);
        }

        if (light == null) {
            light = lightDao.save(new Light(dto.getLevel(), dto.getStatus(), roomDao.getOne(dto.getRoomId()) , dto.getColor(), dto.getBrightness()));
        } else {
            light.setLevel(dto.getLevel());
            light.setStatus(dto.getStatus());
            light.setColor(dto.getColor());
            light.setBrightness(dto.getBrightness());
            lightDao.save(light);
        }

        return new LightDto(light);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        lightDao.deleteById(id);
    }
}