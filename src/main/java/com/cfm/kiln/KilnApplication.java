package com.cfm.kiln;

import com.cfm.kiln.exception.SetupException;
import com.cfm.kiln.hardware.proxy.AirTempHumSensor;
import com.cfm.kiln.hardware.proxy.Indicator;
import com.cfm.kiln.hardware.proxy.Relay;
import com.cfm.kiln.hardware.proxy.impl.AirTempHumSensorDHT22;
import com.cfm.kiln.hardware.proxy.impl.SimpleIndicator;
import com.cfm.kiln.hardware.proxy.impl.SimpleRelay;
import com.cfm.kiln.service.AirTempHumService;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KilnApplication {
    @Autowired
    private static AirTempHumService airTempHumService;

    public static void main(String[] args) {
        SpringApplication.run(KilnApplication.class, args);
        testOperation();

    }

    /**
     * This method is used for test purposes only and should be removed afterwards
     */
    public static void testOperation() {
        if (Gpio.wiringPiSetup() == -1) {
            throw new SetupException("DHT_ERROR_GPIO");
        }
        AirTempHumSensor dht22 = new AirTempHumSensorDHT22();
        dht22.init(RaspiPin.GPIO_07);
        AirTempHumSensor dht22_2 = new AirTempHumSensorDHT22();
        dht22_2.init(RaspiPin.GPIO_06);
        Relay relay = new SimpleRelay();
        relay.init(RaspiPin.GPIO_29);

        Indicator ind = new SimpleIndicator();
        ind.init(RaspiPin.GPIO_10);

        for (int i = 0; i < 5; i++) {
            try {
                System.out.println("T1 " + dht22.getAirTemp());
                System.out.println("H1 " + dht22.getAirHum());

                System.out.println("T2 " + dht22_2.getAirTemp());
                System.out.println("H2 " + dht22_2.getAirHum());
                relay.open();
                ind.switchState();
                Thread.sleep(1000);
                relay.close();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
