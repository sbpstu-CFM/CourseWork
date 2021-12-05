package com.cfm.kiln.controller;

import com.cfm.kiln.hardware.proxy.AirTempHumSensor;
import com.cfm.kiln.hardware.proxy.impl.AirTempHumSensorDHT22;
import com.cfm.kiln.service.AirTempHumService;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/debug/")
public class DebugController {
    private AirTempHumService airTempHumService;

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

    @PostMapping("set-pin/{number}")
    public void setPin(@PathVariable int number, @RequestParam("state") String state) {
        Pin pin = RaspiPin.getPinByAddress(number);
        Gpio.pinMode(pin.getAddress(), Gpio.OUTPUT);
        switch (state.toLowerCase().trim()) {
            case "high" :
                Gpio.digitalWrite(pin.getAddress(), Gpio.HIGH);
                break;
            case "low" :
                Gpio.digitalWrite(pin.getAddress(), Gpio.LOW);
                break;
        }
    }
}
