package com.cfm.kiln.hardware.driver.DHT22;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DHTdata {
    private double temperature;
    private double humidity;
}
