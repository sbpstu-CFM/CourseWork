package com.cfm.kiln.hardware.proxy;

import com.pi4j.io.gpio.Pin;

public interface Relay {
    void init(Pin pin);

    void open();

    void close();

    boolean isOpen();
}
