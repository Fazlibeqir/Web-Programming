package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "cinema_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Convert
    private UserFullname fullname;

    private String password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ShoppingCart> carts;

    public User() {

    }
    public User(String username, UserFullname fullname, String password, LocalDate dateOfBirth) {
        this.username = username;
        this.fullname=fullname;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }


}
