package com.cfm.kiln.service;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.hardware.proxy.AirTempHumSensor;
import com.cfm.kiln.hardware.proxy.WoodMoistureContentSensor;
import com.cfm.kiln.hardware.proxy.impl.MockWoodMoistureContentSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WoodMoistureContentService {
    private List<WoodMoistureContentSensor> sensors;
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WoodMoistureContentService() {
        sensors = new ArrayList<>();
    }

    public void addSensor(WoodMoistureContentSensor toAdd) {
        sensors.add(toAdd);
    }

    public double getData() {
        log.debug("Attempt to get wood MC from {} sensors", sensors.size());
        double res = 0d;
        int okCount = 0;
        for (WoodMoistureContentSensor sensor : sensors) {
            try {
                res += sensor.getValue();
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
        res /= okCount;
        log.info("Mocked wood MC: {}", res);
        return res;
    }
}
