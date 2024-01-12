package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class TicketOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Long numberOfTickets;

    @Column(name = "date_created")
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime dateCreated;

    public TicketOrder(User user, Movie movie, Long numberOfTickets,LocalDateTime dateCreated) {
        this.user = user;
        this.movie = movie;
        this.numberOfTickets = numberOfTickets;
        this.dateCreated=dateCreated;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfTickets, dateCreated);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TicketOrder)) return false;
        TicketOrder other = (TicketOrder) obj;
        return id != null && id.equals(other.id);
    }
}
