package com.cfm.kiln.hardware.proxy.impl;

import com.cfm.kiln.exception.SetupException;
import com.cfm.kiln.hardware.proxy.Relay;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.Gpio;
import lombok.Getter;

public class SimpleRelay implements Relay {
    @Getter
    private GpioPinDigitalOutput pin;
    final GpioController gpio = GpioFactory.getInstance();
    private boolean isOpen = false;

    public void init(Pin pin) {
        this.pin = gpio.provisionDigitalOutputPin(pin);
        if (Gpio.wiringPiSetup() == -1) {
            throw new SetupException("RELAY_ERROR_GPIO");
        }
        Gpio.pinMode(pin.getAddress(), Gpio.OUTPUT);
    }

    public void open() {
        this.isOpen = true;
        try {
            pin.high();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.isOpen = false;
        pin.low();
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    protected void finalize() {
        gpio.shutdown();
    }
}
