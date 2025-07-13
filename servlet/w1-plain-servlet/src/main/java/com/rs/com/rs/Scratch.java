package com.rs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;

public class Scratch {
    public static void main(String[] args) {
        jackson_serialization();
        jackson_deserialization();
    }

    public static void jackson_serialization(){
        Location l = new Location(38.8951, -77.0364, 0 );
        SensorReading sr = new SensorReading("a2",
                SensorType.Humidity,
                "10",
                LocalDateTime.of(2010,1,1,1, 1, 1, 1),
                l
        );
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            System.out.println(objectMapper.writeValueAsString(sr));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void jackson_deserialization(){
        var x = """
                {
                  "nodeId" : "a2",
                  "sensorType" : "Humidity",
                  "reading" : "10",
                  "timestamp" : "2010-01-01 01:01:01:123456123",
                  "location" : {
                    "latitude" : 38.8951,
                    "longitude" : -77.0364,
                    "altitude" : 0.0
                  }
                }
                """;
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        try {
            var sr = objectMapper.readValue(x, SensorReading.class);
            System.out.println(sr);
            System.out.println(sr.timestamp().getNano());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
