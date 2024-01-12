package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.Production;
import mk.finki.ukim.mk.lab.model.dto.MovieDto;
import mk.finki.ukim.mk.lab.repository.jpa.MovieRepositoryInterface;
import mk.finki.ukim.mk.lab.repository.jpa.ProductionRepositoryInterface;
import mk.finki.ukim.mk.lab.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepositoryInterface movieRepositoryInterface;
    private final ProductionRepositoryInterface productionRepositoryInterface;
    @Autowired
    public MovieServiceImpl(MovieRepositoryInterface movieRepositoryInterface,
                            ProductionRepositoryInterface productionRepositoryInterface){
                                  this.movieRepositoryInterface = movieRepositoryInterface;
        this.productionRepositoryInterface = productionRepositoryInterface;
     }

    @Override
    public List<Movie> listAll() {
        return movieRepositoryInterface.findAll();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepositoryInterface.findById(id);
    }


    @Override
    public Optional<Movie> save(String title, String summary, double rating, Long production) {
        Optional<Production> productionOptional = productionRepositoryInterface.findById(production);
        if (productionOptional.isPresent()) {
            Movie movie = new Movie(title, summary, rating, productionOptional.get());
            return Optional.of(movieRepositoryInterface.save(movie));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> save(MovieDto movieDto) {
        Movie movie = convertDtoToMovie(movieDto);
        return Optional.of(movieRepositoryInterface.save(movie));
    }

    private Movie convertDtoToMovie(MovieDto movieDto) {
        Movie movie=new Movie();
        updateMovieFromDto(movie, movieDto);
        return movie;
    }

    private void updateMovieFromDto(Movie movie, MovieDto movieDto) {
        movie.setTitle(movieDto.getTitle());
        movie.setSummary(movieDto.getSummary());
        movie.setRating(movieDto.getRating());
        Production production = productionRepositoryInterface.findById(movieDto.getProduction().getId())
                .orElseThrow(() -> new RuntimeException("Production not found"));
        movie.setProduction(production);
    }

    @Override
    public Optional<Movie> edit(Long id, String title, String summary, double rating, Long production) {
        Optional<Movie> optionalMovie = movieRepositoryInterface.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setTitle(title);
            movie.setSummary(summary);
            movie.setRating(rating);

            Optional<Production> productionOptional = productionRepositoryInterface.findById(production);
            productionOptional.ifPresent(movie::setProduction);

            return Optional.of(movieRepositoryInterface.save(movie));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> edit(Long id, MovieDto movieDto) {
       Optional<Movie> optionalMovie=movieRepositoryInterface.findById(id);
       if(optionalMovie.isPresent()){
           Movie movie=optionalMovie.get();
           updateMovieFromDto(movie,movieDto);
           return Optional.of(movieRepositoryInterface.save(movie));
       }
       return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        movieRepositoryInterface.deleteById(id);
    }
}
