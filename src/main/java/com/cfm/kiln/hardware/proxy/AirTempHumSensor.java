package com.cfm.kiln.hardware.proxy;

import com.cfm.kiln.hardware.driver.DHT22.DHTdata;
import com.pi4j.io.gpio.Pin;

public interface AirTempHumSensor {
    void init(Pin pin) throws Exception;

    double getAirTemp() throws Exception;

    double getAirHum() throws Exception;
}
