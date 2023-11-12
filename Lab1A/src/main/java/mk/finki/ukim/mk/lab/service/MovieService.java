package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.Production;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> listAll();
    Optional<Movie> findById(Long id);
    void deleteById(Long id);

    Optional<Movie> save(String title, String summary, double rating, Long production);
}
