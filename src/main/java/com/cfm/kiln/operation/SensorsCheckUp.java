package com.cfm.kiln.operation;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.hardware.proxy.WoodMoistureContentSensor;
import com.cfm.kiln.service.AirTempHumService;
import com.cfm.kiln.service.ExecutionService;
import com.cfm.kiln.service.HardwareManagerService;
import com.cfm.kiln.service.WoodMoistureContentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorsCheckUp implements Runnable{
    private final AirTempHumService airTempHumService;
    private final WoodMoistureContentService woodMoistureContentService;
    private final ExecutionService executionService;
    private final HardwareManagerService hardwareManagerService;

    private final double THRESHOLD = 2.0;
    private final int FAN_LOAD_STEP = 20;
    private final int HEATER_LOAD_STEP = 30;
    private int fanLoad = 0;
    private int heaterLoad = 0;
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        try {
            AirTemperatureHumidityDTO actual = airTempHumService.getData();
            AirTemperatureHumidityDTO desired = executionService.getDesiredCurrentState(woodMoistureContentService.getData());
            log.info("Sensors check-up: \n desired: {}, \n actual: {}", desired, actual);
            if (desired == null || actual == null) return;
            changeDeviceStates(desired, actual);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void changeDeviceStates(AirTemperatureHumidityDTO desired, AirTemperatureHumidityDTO actual) {
        double temperatureDifference = desired.getTemperature() - actual.getTemperature();
        double humidityDifference = actual.getHumidity() - desired.getHumidity();
        if(Math.abs(temperatureDifference) >= THRESHOLD) {
            int load = calculateLoad(temperatureDifference, heaterLoad, HEATER_LOAD_STEP);
            log.info("Loading heaters at {}", load);
            hardwareManagerService.runHeaters(load);
            heaterLoad = load;
        }
        if(Math.abs(humidityDifference) >= THRESHOLD) {
            int load = calculateLoad(humidityDifference, fanLoad, FAN_LOAD_STEP);
            log.info("Loading fans at {}", load);
            hardwareManagerService.runFans(load);
            fanLoad = load;
        }
    }

    private int calculateLoad(double difference, int load, int step) {
        if(difference > 0) {
            if ((load += step) > 100) {
                load = 100;
            }
        } else {
            if ((load -= step) < 0) {
                load = 0;
            }
        }
        return load;
    }
}
