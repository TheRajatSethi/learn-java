package com.rs.trykube.services;

import com.rs.trykube.dataobjects.Movie;
import com.rs.trykube.persistance.ServiceDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final ServiceDAO serviceDAO;

    public List<Movie> getAllMovies(){
        log.debug("Enter : " + this.getClass().toString() + " getAllMovies");
        return serviceDAO.getAllMovies();
    }

    public Movie getMovieById(int id){
        log.debug("Enter : " + this.getClass().toString() + " getMovieById");
        return serviceDAO.getMovieById(id);
    }

}
