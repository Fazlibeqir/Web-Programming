package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.Production;
import mk.finki.ukim.mk.lab.model.dto.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> listAll();
    Optional<Movie> findById(Long id);

    Optional<Movie> findByName(String name);
    Optional<Movie> save(String title, String summary, double rating, Long production);

    Optional<Movie> save(MovieDto movieDto);

    Optional<Movie> edit(Long id, String title, String  summary,double rating, Long production);

    Optional<Movie> edit(Long id, MovieDto movieDto);

    void deleteById(Long id);

  }
