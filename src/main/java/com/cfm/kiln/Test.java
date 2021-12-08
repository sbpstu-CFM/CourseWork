package com.cfm.kiln;

import com.cfm.kiln.operation.ScheduleCreator;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try {
            ScheduleCreator.createSchedule(9, 5, 650);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
