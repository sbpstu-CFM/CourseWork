package com.cfm.kiln.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegimeDTO {
    private int minDensity;
    private int maxDensity;
    private List<ScheduleElementDTO> modes;
}
