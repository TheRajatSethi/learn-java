package com.rs.stocks.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    Integer id;
    String email;
    @JsonIgnore
    String password;
    String username;
    @JsonIgnore
    ZonedDateTime created_at;
}
