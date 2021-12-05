package com.cfm.kiln.hardware.proxy.impl;

import com.cfm.kiln.exception.SetupException;
import com.cfm.kiln.hardware.proxy.Relay;
import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.Gpio;
import lombok.Getter;

public class SimpleRelay implements Relay {
    @Getter
    private Pin pin;
    private boolean isOpen = false;

    public void init(Pin pin) {
        this.pin = pin;
        if (Gpio.wiringPiSetup() == -1) {
            throw new SetupException("RELAY_ERROR_GPIO");
        }
        Gpio.pinMode(pin.getAddress(), Gpio.OUTPUT);
    }

    public void open() {
        isOpen = true;
        Gpio.digitalWrite(pin.getAddress(), Gpio.HIGH);
    }

    public void close() {
        isOpen = false;
        Gpio.digitalWrite(pin.getAddress(), Gpio.LOW);
    }

    public boolean isOpen() {
        return isOpen;
    }
}
