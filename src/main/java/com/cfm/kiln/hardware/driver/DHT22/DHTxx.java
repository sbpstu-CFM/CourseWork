package com.cfm.kiln.hardware.driver.DHT22;

import com.cfm.kiln.exception.DHT22DataException;
import com.pi4j.io.gpio.Pin;

public interface DHTxx {
    void init() throws Exception;

    Pin getPin();

    void setPin(Pin pin);

    DHTdata getData() throws DHT22DataException;
}
