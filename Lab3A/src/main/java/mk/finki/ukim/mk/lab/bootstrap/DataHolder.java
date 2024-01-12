package mk.finki.ukim.mk.lab.bootstrap;

import lombok.Getter;
import mk.finki.ukim.mk.lab.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DataHolder {
    public static List<User> users= new ArrayList<>();
    public static List<Movie> movies= new ArrayList<>();
    public static List<Production> productions= new ArrayList<>();
    public static List<ShoppingCart> shoppingCarts= new ArrayList<>();
    public static List<TicketOrder> ticketOrders= new ArrayList<>();

}
