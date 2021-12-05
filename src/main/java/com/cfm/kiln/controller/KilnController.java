package com.cfm.kiln.controller;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.service.AirTempHumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KilnController {
    private AirTempHumService airTempHumService;

    @GetMapping("/air-temp-hum")
    @ResponseBody
    public AirTemperatureHumidityDTO getAirTemperatureAndHumidity() {
        return airTempHumService.getData();
    }
}

