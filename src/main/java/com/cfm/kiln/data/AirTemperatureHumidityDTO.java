package com.cfm.kiln.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirTemperatureHumidityDTO {
    private double temperature;
    private double humidity;
}
