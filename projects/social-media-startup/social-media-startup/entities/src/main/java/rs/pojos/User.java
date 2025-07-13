package rs.pojos;

import java.util.UUID;

public record User(String email, String password, String name, Integer id, UUID uuid) { }
