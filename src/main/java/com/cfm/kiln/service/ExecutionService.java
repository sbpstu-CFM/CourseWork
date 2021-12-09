package com.cfm.kiln.service;

import com.cfm.kiln.data.*;
import com.cfm.kiln.exception.SetupException;
import com.cfm.kiln.hardware.proxy.AirTempHumSensor;
import com.cfm.kiln.hardware.proxy.Fan;
import com.cfm.kiln.hardware.proxy.Heater;
import com.cfm.kiln.hardware.proxy.WoodMoistureContentSensor;
import com.cfm.kiln.hardware.proxy.impl.AirTempHumSensorDHT22;
import com.cfm.kiln.hardware.proxy.impl.MockWoodMoistureContentSensor;
import com.cfm.kiln.hardware.proxy.impl.SimpleFan;
import com.cfm.kiln.hardware.proxy.impl.SimpleHeater;
import com.cfm.kiln.operation.Algorithm;
import com.cfm.kiln.operation.ScheduleCreator;
import com.cfm.kiln.operation.SensorsCheckUp;
import com.pi4j.io.gpio.RaspiPin;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ExecutionService {
    private final HardwareManagerService hardwareManagerService;
    private final AirTempHumService airTempHumService;
    private final WoodMoistureContentService woodMoistureContentService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Algorithm algorithm;
    Logger log = LoggerFactory.getLogger(this.getClass());

    public AirTemperatureHumidityDTO getDesiredCurrentState(double currentMoistureContent) {
        if (algorithm == null) {
            return null;
        }
        ScheduleElementDTO state = algorithm.getState(currentMoistureContent);
        if(state == null) {
            log.info("\n============================\n    Algorithm finished!\n===============================");
            this.algorithm = null;
            hardwareManagerService.stopHeaters();
            hardwareManagerService.stopFans();
            scheduler.shutdown();
            return null;
        }
        return CustomConverter.convertToAirTempHumidity(state);
    }

    public void startExecution(AlgorithmParametersDTO parameters) throws IOException {
        this.algorithm = new Algorithm(ScheduleCreator.createSchedule(parameters), parameters.getEndMoistureContent());
        log.info("Devices are set up");
        scheduler.scheduleAtFixedRate(new SensorsCheckUp(airTempHumService, woodMoistureContentService, this, hardwareManagerService), 0, 30, TimeUnit.SECONDS);
    }

    public void deviceSetUp() {
        AirTempHumSensor dht22 = new AirTempHumSensorDHT22();
        dht22.init(RaspiPin.GPIO_02);
        AirTempHumSensor dht22_2 = new AirTempHumSensorDHT22();
        dht22_2.init(RaspiPin.GPIO_03);
        airTempHumService.addSensor(dht22);
        airTempHumService.addSensor(dht22_2);

        WoodMoistureContentSensor woodMoistureContentSensor = new MockWoodMoistureContentSensor();
        woodMoistureContentService.addSensor(woodMoistureContentSensor);

        Heater heater1 = new SimpleHeater();
        heater1.init(RaspiPin.GPIO_21);
        Heater heater2 = new SimpleHeater();
        heater2.init(RaspiPin.GPIO_22);
        Heater heater3 = new SimpleHeater();
        heater3.init(RaspiPin.GPIO_26);

        Fan fan1 = new SimpleFan();
        fan1.init(RaspiPin.GPIO_28);
        Fan fan2 = new SimpleFan();
        fan2.init(RaspiPin.GPIO_29);
        Fan fan3 = new SimpleFan();
        fan3.init(RaspiPin.GPIO_25);

        hardwareManagerService.addFan(fan1);
        hardwareManagerService.addFan(fan2);
        //hardwareManagerService.addFan(fan3);
        hardwareManagerService.addHeater(heater1);
        hardwareManagerService.addHeater(heater2);
        hardwareManagerService.addHeater(heater3);
        System.out.println("Running all");
        hardwareManagerService.runFans(100);
        hardwareManagerService.runHeaters(100);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping all");
        hardwareManagerService.stopHeaters();
        hardwareManagerService.stopFans();
    }
}
