package com.cfm.kiln;

import com.cfm.kiln.hardware.driver.DHT22.DHT22;
import com.cfm.kiln.hardware.driver.DHT22.DHTxx;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KilnApplication {

    public static void main(String[] args) {
        SpringApplication.run(KilnApplication.class, args);
    }

}
