package com.cfm.kiln.hardware.proxy;

import com.pi4j.io.gpio.Pin;

public interface Indicator{
    void init(Pin pin);

    void set();

    void unset();

    void switchState();

    boolean isSet();
}
