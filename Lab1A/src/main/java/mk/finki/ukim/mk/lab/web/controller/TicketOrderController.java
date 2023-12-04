package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.TicketOrder;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.MovieService;
import mk.finki.ukim.mk.lab.service.TicketOrderService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ticket-order")
public class TicketOrderController {
    private final TicketOrderService ticketOrderService;
    private final MovieService movieService;

    public TicketOrderController(TicketOrderService ticketOrderService,
        MovieService movieService) {
        this.ticketOrderService = ticketOrderService;
        this.movieService=movieService;
    }
    @GetMapping
    public String showTicketOrderForm(Model model) {
        List<Movie> movies = movieService.listAll();
        model.addAttribute("movies", movies);
        return "ticket-order-form";
    }
    @GetMapping("/orders-in-time-interval")
    public String getOrdersWithinTimeInterval(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime to,
            Model model) {
        List<TicketOrder> orders = ticketOrderService.getOrdersWithinTimeInterval(from, to);
        model.addAttribute("orders", orders);
        return "order-confirmation";
    }
    @PostMapping
    public String placeTicketOrder(@RequestParam Long movieId,
                                   @RequestParam int numberOfTickets,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateCreated,
                                   Model model,
                                   HttpServletRequest req) {
        User username = (User) req.getSession().getAttribute("user");
        Optional<Movie> optionalMovie = movieService.findById(movieId);
        if(optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            TicketOrder ticketOrder = ticketOrderService.placeOrder(username, movie, numberOfTickets, dateCreated);
            model.addAttribute("ticketOrder", ticketOrder);
            return "order-confirmation";
        }else {
            return "redirect:/movies";
        }
    }
}
