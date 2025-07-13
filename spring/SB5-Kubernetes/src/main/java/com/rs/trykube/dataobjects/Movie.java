package com.rs.trykube.dataobjects;

import java.util.List;

public record Movie(
        int id,
        String title,
        int releaseYear,
        List<Person> people
) {}
