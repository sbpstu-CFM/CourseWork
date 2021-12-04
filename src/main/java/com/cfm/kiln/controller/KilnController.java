package com.cfm.kiln.controller;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.hardware.proxy.AirTempHumSensor;
import com.cfm.kiln.hardware.proxy.impl.AirTempHumSensorDHT22;
import com.cfm.kiln.service.AirTempHumService;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KilnController {
    @Autowired
    private AirTempHumService airTempHumService;

    @GetMapping("/air-temp-hum")
    @ResponseBody
    public AirTemperatureHumidityDTO getAirTemperatureAndHumidity() {
        return airTempHumService.getData();
    }

    @PostMapping("/test")
    @ResponseBody
    public String echoPhrase() {
        AirTempHumSensor dht22 = new AirTempHumSensorDHT22();
        dht22.init(RaspiPin.GPIO_07);
        AirTempHumSensor dht22_2 = new AirTempHumSensorDHT22();
        dht22_2.init(RaspiPin.GPIO_06);
        airTempHumService.addSensor(dht22);
        airTempHumService.addSensor(dht22_2);
        return "Hi, I'm online ╰(*°▽°*)╯";
    }
}
