package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.TicketOrder;
import mk.finki.ukim.mk.lab.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketOrderService {
    TicketOrder placeOrder(User user, Movie movie, int numberOfTickets, LocalDateTime dateCreated);

    List<TicketOrder> getAllOrders();

    List<TicketOrder> getOrdersByUser(Long userId);

    List<TicketOrder> getOrdersByMovie(Long movieId);
    List<TicketOrder> getOrdersWithinTimeInterval(LocalDateTime from, LocalDateTime to);

    List<TicketOrder> getOrdersByUserAndMovie(Long userId, Long movieId);

}
