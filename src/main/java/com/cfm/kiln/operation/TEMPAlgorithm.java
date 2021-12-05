package com.cfm.kiln.operation;

import com.cfm.kiln.data.AirTemperatureHumidityDTO;
import com.cfm.kiln.data.CustomObjectMapper;
import com.cfm.kiln.data.TEMPSchedulePoint;
import com.cfm.kiln.data.WoodTypeDTO;
import com.cfm.kiln.exception.SetupException;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class for test-mock purposes
 */
@Component
public class TEMPAlgorithm {
    private List<TEMPSchedulePoint> schedule;
    private LocalDateTime start;
    @Setter
    private WoodTypeDTO woodType;

    public TEMPAlgorithm() {
        try {
            schedule = CustomObjectMapper.readSchedule();
        } catch (IOException e) {
            throw new SetupException("Schedule reading failed: " + e.getMessage());
        }
        start = LocalDateTime.now();
    }

    public boolean hasAlgorithm(LocalDateTime neededTime) {
        return schedule.isEmpty() && timeFitsSchedule(neededTime);
    }

    public AirTemperatureHumidityDTO getStateAtTime(LocalDateTime neededTime) {
        return getSchedulePointAtTime(neededTime).getAirParams();
    }

    private TEMPSchedulePoint getSchedulePointAtTime(LocalDateTime neededTime) {
        return schedule.stream()
                .filter(schedulePoint -> schedulePoint.getStartTime().isBefore(neededTime) ||
                        schedulePoint.getEndTime().isAfter(neededTime) ||
                        schedulePoint.getStartTime().equals(neededTime))
                .findAny().orElse(null);
    }

    private boolean timeFitsSchedule(LocalDateTime neededTime) {
        return getSchedulePointAtTime(neededTime) != null;
    }
}
