package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepositoryInterface extends JpaRepository<Movie,Long> {
    Optional<Movie> findByTitle(String title);
    void deleteByTitle(String title);

}
