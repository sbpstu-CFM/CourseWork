package com.cfm.kiln.hardware.proxy.impl;

import com.cfm.kiln.exception.SetupException;
import com.cfm.kiln.hardware.proxy.Indicator;
import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.Gpio;

public class SimpleIndicator implements Indicator {
    private Pin pin;
    private boolean isSet = false;

    public void init(Pin pin) {
        this.pin = pin;
        if (Gpio.wiringPiSetup() == -1) {
            throw new SetupException("INDICATOR_ERROR_GPIO");
        }
        Gpio.pinMode(pin.getAddress(), Gpio.OUTPUT);
    }

    public void set() {
        isSet = true;
        Gpio.digitalWrite(pin.getAddress(), Gpio.HIGH);
    }

    public void unset() {
        isSet = false;
        Gpio.digitalWrite(pin.getAddress(), Gpio.LOW);
    }

    public void switchState() {
        if(isSet) {
            Gpio.digitalWrite(pin.getAddress(), Gpio.LOW);
        } else {
            Gpio.digitalWrite(pin.getAddress(), Gpio.HIGH);
        }
        isSet = !isSet;
    }

    public boolean isSet() {
        return isSet;
    }
}
