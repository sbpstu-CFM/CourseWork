package com.cfm.kiln.hardware.proxy;

import com.pi4j.io.gpio.Pin;

/**
 * TODO: This basic interface should be enlarged to include real fan operation methods
 */
public interface Fan {
    void init(Pin pin);

    void start();

    void stop();
}
