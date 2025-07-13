package com.rs.trykube.controllers;

import com.rs.trykube.dataobjects.Movie;
import com.rs.trykube.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies")
    ResponseEntity<List<Movie>> getMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/movies/{id}")
    ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }
}
