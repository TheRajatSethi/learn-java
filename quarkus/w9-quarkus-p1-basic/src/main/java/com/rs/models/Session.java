package com.rs.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private String id;
    private int userId;
    private ZonedDateTime created;
    private ZonedDateTime max;
}
