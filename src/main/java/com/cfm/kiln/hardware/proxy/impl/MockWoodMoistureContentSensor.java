package com.cfm.kiln.hardware.proxy.impl;

import com.cfm.kiln.hardware.proxy.WoodMoistureContentSensor;
import lombok.Setter;

public class MockWoodMoistureContentSensor implements WoodMoistureContentSensor {
    @Setter
    private double initial = 60d;
    @Override
    public double getValue() {
        double rand = 1d + Math.random() * 10;
        if((initial -= rand) < 0d) return 0d;
        return initial;
    }

    public void restart() {
        this.initial = 60d;
    }
}
