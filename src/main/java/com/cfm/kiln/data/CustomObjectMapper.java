package com.cfm.kiln.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CustomObjectMapper {
    public static List<WoodTypeDTO> readWoodTypes() throws IOException {
        TypeReference<List<WoodTypeDTO>> tr = new TypeReference<>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/woodtype.json"), tr);
    }
}
