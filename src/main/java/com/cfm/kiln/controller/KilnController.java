package com.cfm.kiln.controller;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.data.AlgorithmParametersDTO;
import com.cfm.kiln.service.AirTempHumService;
import com.cfm.kiln.service.ExecutionService;
import com.cfm.kiln.service.HardwareManagerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class KilnController {
    private final AirTempHumService airTempHumService;
    private final ExecutionService executionService;
    private final HardwareManagerService hardwareManagerService;
    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/air-temp-hum")
    @ResponseBody
    public AirTemperatureHumidityDTO getAirTemperatureAndHumidity() {
        return airTempHumService.getData();
    }

    @PostMapping("/stop")
    @ResponseBody
    public String emergencyStop() {
        hardwareManagerService.stopFans();
        hardwareManagerService.stopHeaters();
        return "The devices are stopped o((⊙﹏⊙))o.";
    }

    @PostMapping("/setup")
    @ResponseBody
    public String deviceSetup() {
        executionService.deviceSetUp();
        return "The devices are set up (o゜▽゜)o☆";
    }

    @PostMapping("/start")
    @ResponseBody
    public String start(@RequestBody AlgorithmParametersDTO parameters) {
        try {
            executionService.startExecution(parameters);
            log.info("The algorithm with paramaters " + parameters + " has started");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "The algorithm with paramaters " + parameters + " has started";
    }
}

