package mk.ukim.finki.wp.kol2023.g1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Player {

    public Player() {
    }

    public Player(String name, String bio, Double pointsPerGame, PlayerPosition position, Team team) {
        this.name = name;
        this.bio = bio;
        this.pointsPerGame = pointsPerGame;
        this.position = position;
        this.team = team;
        this.votes = 0;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String bio;

    private Double pointsPerGame;

    @Enumerated(EnumType.STRING)
    private PlayerPosition position;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    private Integer votes = 0;

}
