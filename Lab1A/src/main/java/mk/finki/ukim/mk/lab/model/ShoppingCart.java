package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import mk.finki.ukim.mk.lab.model.enumerations.ShoppingCartStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreated;
    @ManyToMany
    private List<TicketOrder> ticketOrders;
    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;
    public ShoppingCart() {
    }
    public ShoppingCart(User user) {
        this.user = user;
        this.dateCreated = LocalDateTime.now();
        this.ticketOrders = new ArrayList<>();
        this.status=ShoppingCartStatus.CREATED;
    }
}
