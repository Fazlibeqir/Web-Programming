package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {
    public List<Movie> movies;
    public MovieRepository() {
        movies = new ArrayList<>();
        movies.add(new Movie("The Shawshank Redemption", "Drama",  9.3));
        movies.add(new Movie("The Godfather", "Crime",  9.2));
        movies.add(new Movie("The Dark Knight", "Action",  9.0));
        movies.add(new Movie("The Godfather: Part II", "Crime",  9.0));
        movies.add(new Movie("The Lord of the Rings: The Return of the King", "Adventure",  8.9));
        movies.add(new Movie("Pulp Fiction", "Crime",  8.9));
        movies.add(new Movie("Schindler's List", "Biography",  8.9));
        movies.add(new Movie("Avengers: End Game", "Sci-fi",  9.9));
        movies.add(new Movie("SuperMan", "Hero",  8.9));
        movies.add(new Movie("Spider-Man", "Hero",  10.0));


    }
    public List<Movie> findAll() {
        return movies;
    }
    public List<Movie> searchMovies(String searchText, double minRating) {
        return movies.stream()
                .filter(movie -> movie.getTitle().contains(searchText) && movie.getRating() >= minRating)
                .collect(Collectors.toList());
    }

}
