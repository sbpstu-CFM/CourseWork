package com.cfm.kiln.service;

import com.cfm.kiln.data.*;
import com.cfm.kiln.exception.SetupException;
import com.cfm.kiln.hardware.proxy.Fan;
import com.cfm.kiln.hardware.proxy.Heater;
import com.cfm.kiln.hardware.proxy.impl.SimpleFan;
import com.cfm.kiln.hardware.proxy.impl.SimpleHeater;
import com.cfm.kiln.operation.Algorithm;
import com.cfm.kiln.operation.ScheduleCreator;
import com.cfm.kiln.operation.SensorsCheckUp;
import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ExecutionService {
    private HardwareManagerService hardwareManagerService;
    private List<WoodTypeDTO> woodTypes;
    private Algorithm algorithm;
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

    public void startExecution(AlgorithmParametersDTO parameters) throws IOException {
        this.algorithm = new Algorithm(ScheduleCreator.createSchedule(parameters));

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SensorsCheckUp(), 0, 1, TimeUnit.MINUTES);
    }

    public AirTemperatureHumidityDTO getDesiredCurrentState(double currentMoistureContent) {
        if (algorithm == null) {
            throw new IllegalStateException("No algorithm is defined");
        }
        return CustomConverter.convertToAirTempHumidity(algorithm.getState(currentMoistureContent));
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
