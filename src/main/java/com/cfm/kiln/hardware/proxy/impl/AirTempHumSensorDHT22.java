package com.cfm.kiln.hardware.proxy.impl;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.hardware.driver.DHT22.DHT22;
import com.cfm.kiln.hardware.driver.DHT22.DHTxx;
import com.cfm.kiln.hardware.proxy.AirTempHumSensor;
import com.pi4j.io.gpio.Pin;

public class AirTempHumSensorDHT22 implements AirTempHumSensor {
    private DHTxx dht22;

    @Override
    public void init(Pin pin){
        this.dht22 = new DHT22(pin);
        this.dht22.init();
    }

    @Override
    public double getAirTemp(){
        return(dht22.getData().getTemperature());
    }

    @Override
    public double getAirHum(){
        return(dht22.getData().getHumidity());
    }

    @Override
    public AirTemperatureHumidityDTO getTH() {
        return dht22.getData();
    }
}
