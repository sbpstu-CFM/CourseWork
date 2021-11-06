package com.cfm.kiln.hardware.proxy.impl;

import com.cfm.kiln.hardware.driver.DHT22.DHT22;
import com.cfm.kiln.hardware.driver.DHT22.DHTxx;
import com.cfm.kiln.hardware.proxy.AirTempHumSensor;
import com.pi4j.io.gpio.Pin;

public class AirTempHumSensorDHT22 implements AirTempHumSensor {
    private DHTxx dht22;

    @Override
    public void init(Pin pin) throws Exception {
        this.dht22 = new DHT22(pin);
        this.dht22.init();
    }

    @Override
    public double getAirTemp() throws Exception {
        try {
            return(dht22.getData().getTemperature());
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception();
    }

    @Override
    public double getAirHum() throws Exception {
        try {
            return(dht22.getData().getHumidity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception();
    }
}
