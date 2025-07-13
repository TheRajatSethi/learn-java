package com.rs.trykube.persistance;

import com.rs.trykube.dataobjects.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class ServiceDAO {
    private final JdbcTemplate jdbcTemplate;

    public List<Movie> getAllMovies() {
        log.debug("Enter : " + this.getClass().toString() + " getAllMovies");

        String getAllMovies = """
                SELECT * from Movie
                """;

        return jdbcTemplate.query(getAllMovies, new MovieMapper());

        /**
         * My preferred way of doing things but there seems to be a bug with queryForStream where a connection leak
         * happens.
         * https://mjchi7.github.io/posts/jdbctemplate-queryforstream-connection-leak/
         */
//        return jdbcTemplate.queryForStream(getAllMovies, mew MovieMapper())
//                .collect(Collectors.toList());
    }

    public Movie getMovieById(int id){
        log.debug("Enter : " + this.getClass().toString() + " getMovieById");
        log.debug("Requested Movie Id "+id);

        String getMovieById = """
                SELECT * from Movie where id = %s
                """.formatted(id);

        return jdbcTemplate.queryForObject(getMovieById, new MovieMapper());
    }
}
