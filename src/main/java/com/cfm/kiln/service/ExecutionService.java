package com.cfm.kiln.service;

import com.cfm.kiln.data.CustomObjectMapper;
import com.cfm.kiln.data.WoodTypeDTO;
import com.cfm.kiln.exception.SetupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class ExecutionService {
    private List<WoodTypeDTO> woodTypes;
    Logger log = LoggerFactory.getLogger(this.getClass());

    public ExecutionService() {
        try {
            woodTypes = CustomObjectMapper.readWoodTypes();
            log.info("Wood types red successfully: " + woodTypes);
        } catch (IOException e) {
            log.error("Exception while reading wood types: " + e.getMessage());
            throw new SetupException("Wood types reading failed");
        }
    }


}
