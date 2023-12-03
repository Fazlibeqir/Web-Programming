package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.TicketOrder;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.jpa.TicketOrderRepositoryInterface;
import mk.finki.ukim.mk.lab.service.TicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketOrderServiceImpl implements TicketOrderService {
    private final TicketOrderRepositoryInterface ticketOrderRepositoryInterface;

    @Autowired
    public TicketOrderServiceImpl(TicketOrderRepositoryInterface ticketOrderRepositoryInterface) {
        this.ticketOrderRepositoryInterface = ticketOrderRepositoryInterface;
    }
    @Override
    public TicketOrder placeOrder(User user, Movie movie, int numberOfTickets, LocalDateTime dateCreated) {
        TicketOrder ticketOrder=new TicketOrder(user,movie,(long)numberOfTickets,dateCreated);
        return ticketOrderRepositoryInterface.save(ticketOrder);
    }

    @Override
    public List<TicketOrder> getOrdersWithinTimeInterval(LocalDateTime from, LocalDateTime to) {
        return ticketOrderRepositoryInterface.findByDateCreatedBetween(from, to);
    }

}
