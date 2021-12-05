package com.cfm.kiln.service;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.data.CustomObjectMapper;
import com.cfm.kiln.data.WoodTypeDTO;
import com.cfm.kiln.exception.SetupException;
import com.cfm.kiln.hardware.proxy.Fan;
import com.cfm.kiln.hardware.proxy.Heater;
import com.cfm.kiln.hardware.proxy.Indicator;
import com.cfm.kiln.hardware.proxy.Relay;
import com.cfm.kiln.hardware.proxy.impl.SimpleFan;
import com.cfm.kiln.hardware.proxy.impl.SimpleHeater;
import com.cfm.kiln.hardware.proxy.impl.SimpleIndicator;
import com.cfm.kiln.hardware.proxy.impl.SimpleRelay;
import com.cfm.kiln.operation.SensorsCheckUp;
import com.cfm.kiln.operation.TEMPAlgorithm;
import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ExecutionService {
    private List<WoodTypeDTO> woodTypes;
    private TEMPAlgorithm algorithm;
    private HardwareManagerService hardwareManagerService;
    Logger log = LoggerFactory.getLogger(this.getClass());


    public ExecutionService() {
        try {
            woodTypes = CustomObjectMapper.readWoodTypes();
            log.info("Wood types red successfully: " + woodTypes);
        } catch (IOException e) {
            log.error("Exception while reading wood types: " + e.getMessage());
            throw new SetupException("Wood types reading failed");
        }
    }

    public AirTemperatureHumidityDTO getDesiredCurrentState() {
        LocalDateTime now = LocalDateTime.now();
        if (algorithm.hasAlgorithm(now)) {
            return algorithm.getStateAtTime(now);
        }
        else return null;
    }

    public void testRun() {
        Heater heater1 = new SimpleHeater();
        heater1.init(RaspiPin.GPIO_29);
        Heater heater2 = new SimpleHeater();
        heater2.init(RaspiPin.GPIO_28);

        Fan fan1 = new SimpleFan();
        fan1.init(RaspiPin.GPIO_10);
        Fan fan2 = new SimpleFan();
        fan2.init(RaspiPin.GPIO_11);

        hardwareManagerService.addFan(fan1);
        hardwareManagerService.addFan(fan2);
        hardwareManagerService.addHeater(heater1);
        hardwareManagerService.addHeater(heater2);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SensorsCheckUp(), 0, 1, TimeUnit.MINUTES);
    }


}
