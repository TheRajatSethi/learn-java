package com.rs.trykube.persistance;

import com.rs.trykube.dataobjects.Movie;
import com.rs.trykube.dataobjects.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Movie(rs.getInt("id"),
        rs.getString("title"),
        rs.getInt("releaseYear"),
        List.of());
    }
}
