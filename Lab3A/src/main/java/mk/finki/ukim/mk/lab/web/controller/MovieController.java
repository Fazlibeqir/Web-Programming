package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.Production;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.MovieService;
import mk.finki.ukim.mk.lab.service.ProductionService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final ProductionService productionService;
    private final UserService userService;

    public MovieController(MovieService movieService, ProductionService productionService, UserService userService) {
        this.movieService = movieService;
        this.productionService = productionService;
        this.userService = userService;
    }

    @GetMapping
    public String getMoviePage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Movie> movies = this.movieService.listAll();
        List<User> users=this.userService.getAllUsers();
        model.addAttribute("movies", movies);
        model.addAttribute("users",users);
        return "listMovies";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        this.movieService.deleteById(id);
        return "redirect:/movies";
    }

    @GetMapping("/edit-form/{id}")
    public String editMoviePage(@PathVariable Long id, Model model) {
        if (this.movieService.findById(id).isPresent()) {
            Movie movie = this.movieService.findById(id).get();
            List<Production> productions = this.productionService.listAll();
            model.addAttribute("productions", productions);
            model.addAttribute("movie", movie);
            return "add-movie";
        }
        return "redirect:/movies?error=MovieNotFound";
    }

    @GetMapping("/add-form")
    public String addMoviePage(Model model) {
        List<Production> productions = this.productionService.listAll();
        model.addAttribute("productions", productions);
        return "add-movie";
    }

    @PostMapping("/add")
    public String saveMovie(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String summary,
            @RequestParam double rating,
            @RequestParam Long productions) {
            if(id!=null){
                this.movieService.edit(id,title, summary, rating, productions);

            }else {
                this.movieService.save(title, summary, rating, productions);
            }
            return "redirect:/movies";
    }
}