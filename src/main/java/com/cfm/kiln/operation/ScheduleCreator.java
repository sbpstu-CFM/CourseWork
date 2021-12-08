package com.cfm.kiln.operation;

import com.cfm.kiln.data.AlgorithmParametersDTO;
import com.cfm.kiln.data.CustomObjectMapper;
import com.cfm.kiln.data.RegimeDTO;
import com.cfm.kiln.data.ScheduleElementDTO;
import com.cfm.kiln.exception.UnsupportedRegimeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleCreator {
    public static List<ScheduleElementDTO> createSchedule(AlgorithmParametersDTO parameters) throws IOException,
                                                                                  UnsupportedRegimeException {
        double startMoistureContent = parameters.getStartMoistureContent();
        double finishMoistureContent = parameters.getEndMoistureContent();
        int woodDensity = parameters.getDensity();
        if (startMoistureContent <= 0 || startMoistureContent >= 100) {
            throw new IllegalArgumentException("startMoistureContent must be less than 100 and more than 0");
        }

        if (finishMoistureContent <= 0 || finishMoistureContent >= 100) {
            throw new IllegalArgumentException("finishMoistureContent must be less than 100 and more than 0");
        }

        if (finishMoistureContent >= startMoistureContent) {
            throw new IllegalArgumentException("finishMoistureContent must be greater than startMoistureContent");
        }

        if (woodDensity <= 0) {
            throw new IllegalArgumentException("woodDensity must be greater than 0");
        }

        List<RegimeDTO> regimeList = CustomObjectMapper.readRegimes();
        if (regimeList.isEmpty()) {
            throw new UnsupportedRegimeException("There are no regimes in the system right now");
        }

        RegimeDTO regime = null;
        for (RegimeDTO temp : regimeList) {
            if (temp.getMaxDensity() >= woodDensity
                    && temp.getMinDensity() <= woodDensity) {
                regime = temp;
                break;
            }
        }

        if (regime == null) {
            throw new UnsupportedRegimeException("There is no regimes for this woodDensity: "
                    + woodDensity
                    + " in the system right now");
        }

        List<ScheduleElementDTO> schedule = regime.getModes();

        while(!schedule.isEmpty() && schedule.get(0).getMinMoistureContent() >= startMoistureContent) {
            schedule.remove(0);
        }

        if (schedule.isEmpty()) {
            throw new UnsupportedRegimeException("There is no regimes for this woodDensity: "
                    + woodDensity
                    + " with this startMoistureContent "
                    + startMoistureContent
                    + " in the system right now");
        }

        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getMinMoistureContent() <= finishMoistureContent) {
                schedule.get(i).setMinMoistureContent(finishMoistureContent);
                while (i != schedule.size() - 1) {
                    schedule.remove(i + 1);
                }
            }
        }

        if (finishMoistureContent < schedule.get(schedule.size() - 1).getMinMoistureContent()) {
            throw new UnsupportedRegimeException("There is no regimes for this woodDensity: "
                    + woodDensity
                    + " with this finishMoistureContent "
                    + finishMoistureContent +
                    " in the system right now");
        }

        return schedule;
    }
}
