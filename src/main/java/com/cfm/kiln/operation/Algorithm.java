package com.cfm.kiln.operation;

import com.cfm.kiln.data.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
public class Algorithm {
    private final List<ScheduleElementDTO> schedule;
    private double finishMoistureContent;
    private LocalDateTime start;
    private final double SWITCH_THRESHOLD = 0.5;

    public Algorithm(List<ScheduleElementDTO> schedule, double finishMoistureContent) {
        this.schedule = schedule;
        this.start = LocalDateTime.now();
        this.finishMoistureContent = finishMoistureContent;
    }

    Comparator<ScheduleElementDTO> scheduleComparator = Comparator.comparingDouble(ScheduleElementDTO::getMinMoistureContent).reversed();

    public ScheduleElementDTO getState(double moistureContent) {
        if(moistureContent <= finishMoistureContent) return null;
        return schedule.stream()
                .filter(state -> state.getMinMoistureContent() < moistureContent - SWITCH_THRESHOLD)
                .min(scheduleComparator)
                .orElseThrow();
    }
}
