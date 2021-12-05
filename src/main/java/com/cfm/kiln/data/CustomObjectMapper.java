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

    public static List<TEMPSchedulePoint> readSchedule() throws IOException {
        TypeReference<List<TEMPSchedulePoint>> tr = new TypeReference<>() {};
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer ldts = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        module.addDeserializer(LocalDateTime.class, ldts);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        return objectMapper.readValue(new File("src/main/resources/schedule.json"), tr);
    }
}
