package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
             return "redirect:/login";
        }
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getUsername());
        model.addAttribute("movies", this.shoppingCartService.listAllTicketsInShoppingCart(shoppingCart.getId()));
        return "shopping-cart";
    }

    @PostMapping("/add-movie/{id}")
    public String addMovieToShoppingCart(@PathVariable Long id,
                                         @RequestParam Long numOfTickets,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm") LocalDateTime dateCreated,
                                         HttpServletRequest req) {
        try {
            User username =(User) req.getSession().getAttribute("user");
            this.shoppingCartService.addMovieToShoppingCart(username.getUsername(), id,numOfTickets,dateCreated);
            return "redirect:/movies";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }
}
