package com.cfm.kiln.operation;

import com.cfm.kiln.data.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class Algorithm {
    private final List<ScheduleElementDTO> schedule;
    private LocalDateTime start;
    private final double SWITCH_THRESHOLD = 0.5;

    public Algorithm(List<ScheduleElementDTO> schedule) {
        this.schedule = schedule;
        this.start = LocalDateTime.now();
    }

    Comparator<ScheduleElementDTO> scheduleComparator = Comparator.comparingDouble(ScheduleElementDTO::getMinMoistureContent).reversed();

    public ScheduleElementDTO getState(double moistureContent) {
        return schedule.stream()
                .filter(state -> state.getMinMoistureContent() < moistureContent - SWITCH_THRESHOLD)
                .min(scheduleComparator)
                .orElseThrow();
    }
}
