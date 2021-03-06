package com.cfm.kiln.hardware.proxy;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.pi4j.io.gpio.Pin;

public interface AirTempHumSensor {
    void init(Pin pin);

    double getAirTemp();

    double getAirHum();

    AirTemperatureHumidityDTO getTH();
}
