package com.cfm.kiln;

import com.cfm.kiln.hardware.driver.DHT22.DHT22;
import com.cfm.kiln.hardware.driver.DHT22.DHTxx;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KilnApplication {

    public static void main(String[] args) {
        SpringApplication.run(KilnApplication.class, args);

            DHTxx dht22 = new DHT22(RaspiPin.GPIO_07);
            System.out.println(dht22);
            try {
                dht22.init();
                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println(dht22.getData());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

    }

}
