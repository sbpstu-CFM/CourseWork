package com.cfm.kiln.hardware.driver.DHT22;

import com.pi4j.io.gpio.Pin;

public interface DHTxx {
    public void init() throws Exception;

    public Pin getPin();

    public void setPin(Pin pin);

    public DHTdata getData() throws Exception;
}
