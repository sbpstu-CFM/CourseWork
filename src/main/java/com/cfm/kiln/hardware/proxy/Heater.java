package com.cfm.kiln.hardware.proxy;

import com.pi4j.io.gpio.Pin;

/**
 * TODO: This basic interface should be enlarged to include real heater operation methods
 */
public interface Heater {
    void init(Pin pin);

    void start();

    void stop();
}
