package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import mk.finki.ukim.mk.lab.service.TicketOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/ticketOrder")
public class TicketOrderController {
    private final ShoppingCartService shoppingCartService;
    private final TicketOrderService ticketOrderService;

    public TicketOrderController(ShoppingCartService shoppingCartService,
                                 TicketOrderService ticketOrderService) {
        this.shoppingCartService = shoppingCartService;
        this.ticketOrderService = ticketOrderService;
    }
    @GetMapping("/showCart")
    public String showShoppingCart(Model model, @RequestParam(required = false) String selectedUser){
        ShoppingCart shoppingCart=shoppingCartService.getActiveShoppingCart(selectedUser);
        model.addAttribute("shoppingCart",shoppingCart);
        return "shopping-cart";
    }
    @PostMapping("/addToCart")
    public String addToShoppingCart(@RequestParam Long movieId,
                                    @RequestParam String selectedUser,
                                    @RequestParam("numberOfTickets") Long numberOfTickets ,
                                    @RequestParam LocalDateTime dateCreated) {
        shoppingCartService.addMovieToShoppingCart(selectedUser, movieId, numberOfTickets,dateCreated);
        return "redirect:/ticketOrder/showCart";
    }
}
