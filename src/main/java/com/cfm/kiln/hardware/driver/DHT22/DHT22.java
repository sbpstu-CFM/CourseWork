package com.cfm.kiln.hardware.driver.DHT22;

import com.cfm.kiln.exception.ChecksumException;
import com.cfm.kiln.exception.DHT22DataException;
import com.cfm.kiln.exception.TimeoutException;
import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.Gpio;

public class DHT22 extends DHTxxBase {

    private static final int DHT_WAIT_INTERVAL = 2000;

    public DHT22(Pin pin) {
        super(pin);
    }

    @Override
    public DHTdata getData() throws DHT22DataException {
        int attempts = 0;
        while (true) {
            try {
                int[] data = getRawData();

                /*
                 * Verify checksum of received data.
                 */
                if (data[4] != ((data[0] + data[1] + data[2] + data[3]) & 0xFF)) {
                    throw new ChecksumException("DHT_ERROR_CHECKSUM");
                }
                /*
                 * Calculate humidity and temp for DHT22 sensor.
                 */
                double humidity = (data[0] * 256 + data[1]) / 10.0;
                double temperature = ((data[2] & 0x7F) * 256 + data[3]) / 10.0;
                if ((data[2] & 0x80) != 0) {
                    temperature *= -1.0f;
                }

                return new DHTdata(temperature, humidity);
            } catch (ChecksumException | TimeoutException e) {
                attempts++;
                if (attempts <= 3) {
                    Gpio.delay(DHT_WAIT_INTERVAL);
                    continue;
                }
                throw new DHT22DataException("Attempts " + attempts, e);
            }
        }
    }

    @Override
    public String toString() {
        return "DHT22, pin: " + getPin();
    }
}