package com.cfm.kiln.controller;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.service.AirTempHumService;
import com.cfm.kiln.service.ExecutionService;
import com.cfm.kiln.service.HardwareManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KilnController {
    private AirTempHumService airTempHumService;
    private ExecutionService executionService;
    private HardwareManagerService hardwareManagerService;

    @GetMapping("/air-temp-hum")
    @ResponseBody
    public AirTemperatureHumidityDTO getAirTemperatureAndHumidity() {
        return airTempHumService.getData();
    }

    @PostMapping("/stop")
    public void emergencyStop() {
        hardwareManagerService.stopFans();
        hardwareManagerService.stopHeaters();
    }
}

