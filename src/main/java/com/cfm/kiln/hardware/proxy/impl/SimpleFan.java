package com.cfm.kiln.hardware.proxy.impl;

import com.cfm.kiln.hardware.proxy.Fan;
import com.cfm.kiln.hardware.proxy.Relay;
import com.pi4j.io.gpio.Pin;

public class SimpleFan implements Fan {
    private Relay relay;

    @Override
    public void init(Pin pin) {
        relay = new SimpleRelay();
        relay.init(pin);
    }

    @Override
    public void start() {
        relay.open();
    }

    @Override
    public void stop() {
        relay.close();
    }
}
