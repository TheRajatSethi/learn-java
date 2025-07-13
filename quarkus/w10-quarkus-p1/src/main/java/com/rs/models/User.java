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
public class User {

    private int id;
    private String uuid;
    private String email;
    private String name;
    private String password;
    private ZonedDateTime created;
    private ZonedDateTime updated;

}