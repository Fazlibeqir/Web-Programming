package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class TicketOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Long numberOfTickets;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    public TicketOrder(User user, Movie movie, Long numberOfTickets,LocalDateTime dateCreated) {
        this.user = user;
        this.movie = movie;
        this.numberOfTickets = numberOfTickets;
        this.dateCreated=dateCreated;
    }
}
