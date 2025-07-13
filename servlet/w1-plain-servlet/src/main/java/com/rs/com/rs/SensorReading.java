package com.rs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record SensorReading(String nodeId,
                            SensorType sensorType,
                            String reading,
                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss:nnnnnnnnn")
                            LocalDateTime timestamp,
                            Location location) {
}
