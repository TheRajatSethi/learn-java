package models;

public record User (
    int id,
    String name,
    String email,
    String password
){}