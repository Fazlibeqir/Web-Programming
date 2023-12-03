package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }
    @GetMapping("/shopping-cart")
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = userService.getCurrentUsername();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("movies", this.shoppingCartService.listAllTicketsInShoppingCart(shoppingCart.getId()));
        return "shopping-cart";
    }

    @PostMapping("/add-movie/{id}")
    public String addMovieToShoppingCart(@PathVariable Long id,
                                         @RequestParam Long numOfTickets,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateCreated,
                                         HttpServletRequest req) {
        try {
            String username =userService.getCurrentUsername();
            this.shoppingCartService.addMovieToShoppingCart(username, id,numOfTickets,dateCreated);
            return "redirect:/ShoppingCart/shopping-cart";
        } catch (RuntimeException exception) {
            return "redirect:/ShoppingCart/shopping-cart?error=" + exception.getMessage();
        }
    }
}
