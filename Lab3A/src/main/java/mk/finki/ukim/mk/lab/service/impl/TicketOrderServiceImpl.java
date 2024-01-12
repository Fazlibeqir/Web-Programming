package mk.finki.ukim.mk.lab.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.TicketOrder;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.jpa.TicketOrderRepositoryInterface;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepositoryInterface;
import mk.finki.ukim.mk.lab.service.TicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketOrderServiceImpl implements TicketOrderService {
    private final TicketOrderRepositoryInterface ticketOrderRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public TicketOrderServiceImpl(TicketOrderRepositoryInterface ticketOrderRepositoryInterface, UserRepositoryInterface userRepositoryInterface) {
        this.ticketOrderRepositoryInterface = ticketOrderRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
    }
    @Override
    @Transactional
    public TicketOrder placeOrder(User user, Movie movie, int numberOfTickets, LocalDateTime dateCreated) {
        if(!entityManager.contains(user)){
            user=entityManager.merge(user);
        }
        TicketOrder ticketOrder=new TicketOrder(user,movie,(long)numberOfTickets,dateCreated);
        user.addTicketOrder(ticketOrder);
        ticketOrderRepositoryInterface.save(ticketOrder);
        userRepositoryInterface.save(user);
        return ticketOrder;
    }

    @Override
    public List<TicketOrder> getOrdersWithinTimeInterval(LocalDateTime from, LocalDateTime to) {
        return ticketOrderRepositoryInterface.findByDateCreatedBetween(from, to);
    }

    @Override
    public List<TicketOrder> listAllOrders() {
        return ticketOrderRepositoryInterface.findAll();
    }

}
