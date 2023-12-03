package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.*;
import mk.finki.ukim.mk.lab.model.enumerations.ShoppingCartStatus;
import mk.finki.ukim.mk.lab.model.exceptions.ShoppingCartNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.UserNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.ShoppingCartRepositoryInterface;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepositoryInterface;
import mk.finki.ukim.mk.lab.service.MovieService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepositoryInterface shoppingCartRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final MovieService movieService;

    public ShoppingCartServiceImpl(ShoppingCartRepositoryInterface shoppingCartRepositoryInterface,
                                   UserRepositoryInterface userRepositoryInterface,
                                   MovieService movieService) {
        this.shoppingCartRepositoryInterface = shoppingCartRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.movieService = movieService;
    }

    @Override
    public List<TicketOrder> listAllMoviesInShoppingCart(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepositoryInterface.findById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException(cartId));
        return shoppingCart.getTicketOrders();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user= this.userRepositoryInterface.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));
        return shoppingCartRepositoryInterface.findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(()->{
                    ShoppingCart cart=new ShoppingCart(user);
                    return shoppingCartRepositoryInterface.save(cart);
                });
    }

    @Override
    public ShoppingCart addMovieToShoppingCart(String username, Long movieId, Long numberOfTickets, LocalDateTime dateCreated) {
        ShoppingCart shoppingCart= getActiveShoppingCart(username);
        Movie movie= movieService.findById(movieId)
                .orElseThrow(()->new RuntimeException("Movie not found"));
        TicketOrder ticketOrder = new TicketOrder(shoppingCart.getUser(),movie,numberOfTickets,dateCreated);
        shoppingCart.getTicketOrders().add(ticketOrder);
        return shoppingCartRepositoryInterface.save(shoppingCart);
    }
}
