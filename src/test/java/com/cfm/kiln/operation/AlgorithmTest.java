package com.cfm.kiln.operation;

import com.cfm.kiln.data.ScheduleElementDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {
    private Algorithm algorithm;
    private final ScheduleElementDTO el10 = ScheduleElementDTO.builder().minMoistureContent(10).humidity(10).temperature(10).build();
    private final ScheduleElementDTO el20 = ScheduleElementDTO.builder().minMoistureContent(20).humidity(20).temperature(20).build();
    private final ScheduleElementDTO el30 = ScheduleElementDTO.builder().minMoistureContent(30).humidity(30).temperature(30).build();
    private final ScheduleElementDTO el40 = ScheduleElementDTO.builder().minMoistureContent(40).humidity(40).temperature(40).build();
    private final double LOW = 12;
    private final double MEDIUM = 25;
    private final double HIGH = 80;
    private final double THRESHOLD = 30.2;

    @BeforeEach
    void setUp() {
        List<ScheduleElementDTO> scheduleElementDTOS = new ArrayList<>();
        scheduleElementDTOS.add(el10);
        scheduleElementDTOS.add(el40);
        scheduleElementDTOS.add(el30);
        scheduleElementDTOS.add(el20);
        algorithm = new Algorithm(scheduleElementDTOS, 0);
    }

    @Test
    void getState_WhenStateIsLow() {
        Assertions.assertEquals(el10, algorithm.getState(LOW));
    }

    @Test
    void getState_WhenStateIsMedium() {
        Assertions.assertEquals(el20, algorithm.getState(MEDIUM));
    }

    @Test
    void getState_WhenStateIsHigh() {
        Assertions.assertEquals(el40, algorithm.getState(HIGH));
    }

    @Test
    void getState_WhenStateIsThreshold() {
        Assertions.assertEquals(el20, algorithm.getState(THRESHOLD));
    }

}