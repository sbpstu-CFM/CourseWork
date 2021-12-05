package com.cfm.kiln.hardware.proxy;

import com.pi4j.io.gpio.Pin;

public interface Device {
    void init(Pin pin);

    void start();

    void stop();

    boolean isRunning();
}
