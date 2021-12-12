package com.cfm.kiln.hardware.driver.DHT22;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.exception.DHT22DataException;
import com.cfm.kiln.exception.SetupException;
import com.pi4j.io.gpio.Pin;

public interface DHTxx {
    void init() throws SetupException;

    Pin getPin();

    void setPin(Pin pin);

    AirTemperatureHumidityDTO getData() throws DHT22DataException;
}
