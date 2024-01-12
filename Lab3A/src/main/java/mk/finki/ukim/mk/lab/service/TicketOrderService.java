package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.TicketOrder;
import mk.finki.ukim.mk.lab.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketOrderService {
    TicketOrder placeOrder(User user, Movie movie, int numberOfTickets, LocalDateTime dateCreated);
    List<TicketOrder> getOrdersWithinTimeInterval(LocalDateTime from, LocalDateTime to);

    List<TicketOrder> listAllOrders();
}
