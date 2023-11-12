package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.Production;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {
    public List<Movie> movies;
    public MovieRepository(ProductionRepository productionRepository) {
        movies = new ArrayList<>();
        movies.add(new Movie("The Shawshank Redemption", "Drama",  9.3,productionRepository.findAll().get(0)));
        movies.add(new Movie("The Godfather: Part II", "Crime",  9.0,productionRepository.findAll().get(1)));
        movies.add(new Movie("Avengers: End Game", "Sci-fi",  9.9,productionRepository.findAll().get(2)));
        movies.add(new Movie("SuperMan", "Hero",  8.9, productionRepository.findAll().get(3)));
        movies.add(new Movie("Spider-Man", "Hero",  10.0, productionRepository.findAll().get(4)));


    }
    public List<Movie> findAll() {
        return movies;
    }
    public Optional<Movie> findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst();
    }
    public void deleteById(Long id) {
        movies.removeIf(movie -> movie.getId().equals(id));
    }

    public Optional<Movie> save(String title, String summary, double rating, Production production) {
        if( production==null)
            throw new RuntimeException("Production not found");
        Movie movie = new Movie(title,summary,rating,production);
        movies.add(movie);
        return Optional.of(movie);
    }

}
