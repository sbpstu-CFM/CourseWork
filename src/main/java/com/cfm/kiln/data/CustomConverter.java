package com.cfm.kiln.data;

public class CustomConverter {
    public static AirTemperatureHumidityDTO convertToAirTempHumidity(ScheduleElementDTO state) {
        return AirTemperatureHumidityDTO.builder().humidity(state.getHumidity()).temperature(state.getTemperature()).build();
    }
}
