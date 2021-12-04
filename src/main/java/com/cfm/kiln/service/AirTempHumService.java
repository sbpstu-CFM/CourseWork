package com.cfm.kiln.service;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.hardware.proxy.AirTempHumSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AirTempHumService {
    private ArrayList<AirTempHumSensor> sensors;
    Logger log = LoggerFactory.getLogger("servLogger");

    public AirTempHumService() {
        sensors = new ArrayList<>();
    }

    public void addSensor(AirTempHumSensor toAdd) {
        sensors.add(toAdd);
    }

    public AirTemperatureHumidityDTO getData() {
        log.info("Attempt to get data from {} sensors", sensors.size());
        double temperature = 0d;
        double humidity = 0d;
        int okCount = 0;
        for (AirTempHumSensor sensor : sensors) {
            try {
                AirTemperatureHumidityDTO fromSensor = sensor.getTH();
                temperature += fromSensor.getTemperature();
                humidity += fromSensor.getHumidity();
                okCount++;
            } catch (RuntimeException e) {
                log.warn("Exception from sensor: " + e.getMessage());
            }
        }
        if (okCount < sensors.size()) {
            log.error("Some sensors failed to respond");
        }
        if (okCount == 0) {
            throw new IllegalStateException("All sensors failed");
        }
        temperature /= okCount;
        humidity /= okCount;
        log.info("Successfully gathered data from {} sensors, \n    Avg. temp = {} \n    Avg. humidity = {}", okCount, temperature, humidity);
        return AirTemperatureHumidityDTO.builder()
                .temperature(temperature)
                .humidity(humidity)
                .build();
    }
}
