package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.TicketOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TicketOrderRepository {
    public List<TicketOrder> findAll(){
        return DataHolder.ticketOrders;
    }
    List<TicketOrder> findByUserId(Long userId){
        return DataHolder.ticketOrders.stream().filter(i->i.getUser().getId().equals(userId)).collect(Collectors.toList());
    }
    List<TicketOrder> findByMovieId(Long movieId){
        return DataHolder.ticketOrders.stream().filter(i->i.getMovie().getId().equals(movieId)).collect(Collectors.toList());

    }
    List<TicketOrder> findByUserIdAndMovieId(Long userId,Long movieId){
        return DataHolder.ticketOrders.stream().filter(i->i.getUser().getId().equals(userId) && i.getMovie().getId().equals(movieId)).collect(Collectors.toList());
    }
}
