package com.cfm.kiln.operation;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.service.AirTempHumService;
import com.cfm.kiln.service.ExecutionService;
import com.cfm.kiln.service.HardwareManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SensorsCheckUp implements Runnable{
    private AirTempHumService airTempHumService;
    private ExecutionService executionService;
    private HardwareManagerService hardwareManagerService;

    private final double THRESHOLD = 2.0;
    private final int FAN_LOAD_STEP = 20;
    private final int HEATER_LOAD_STEP = 30;
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        AirTemperatureHumidityDTO desired = airTempHumService.getData();
        AirTemperatureHumidityDTO actual = executionService.getDesiredCurrentState();
        log.info("Sensors check-up: \n desired: {}, \n actual: {}", desired, actual);
        if(actual == null) return;
        changeDeviceStates(desired, actual);
    }

    private void changeDeviceStates(AirTemperatureHumidityDTO desired, AirTemperatureHumidityDTO actual) {
        double temperatureDifference = desired.getTemperature() - actual.getTemperature();
        double humidityDifference = actual.getHumidity() - desired.getHumidity();
        if(Math.abs(temperatureDifference) >= THRESHOLD) {
            int load = calculateLoad(temperatureDifference, hardwareManagerService.getHeaterLoad(), HEATER_LOAD_STEP);
            hardwareManagerService.runHeaters(load);
        }
        if(Math.abs(humidityDifference) >= THRESHOLD) {
            int load = calculateLoad(humidityDifference, hardwareManagerService.getFanLoad(), FAN_LOAD_STEP);
            hardwareManagerService.runFans(load);
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
