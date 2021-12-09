package com.cfm.kiln.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomObjectMapper {
    public static List<WoodTypeDTO> readWoodTypes() throws IOException {
        TypeReference<List<WoodTypeDTO>> tr = new TypeReference<>() {};
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/woodtype.json"), tr);
    }

    public static List<RegimeDTO> readRegimes() throws IOException {
        TypeReference<List<RegimeDTO>> tr = new TypeReference<>() {};
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("./schedules.json"), tr);
    }
}
