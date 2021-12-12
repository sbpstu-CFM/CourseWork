package com.cfm.kiln.hardware.proxy.impl;

import com.cfm.kiln.hardware.proxy.Heater;
import com.cfm.kiln.hardware.proxy.Relay;
import com.pi4j.io.gpio.Pin;

public class SimpleHeater implements Heater {
    private Relay relay;

    @Override
    public void init(Pin pin) {
        this.relay = new SimpleRelay();
        this.relay.init(pin);
    }

    @Override
    public void start() {
        relay.open();
    }

    @Override
    public void stop() {
        relay.close();
    }

    @Override
    public boolean isRunning() {
        return relay.isOpen();
    }
}
