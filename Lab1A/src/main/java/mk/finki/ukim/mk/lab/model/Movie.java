package mk.finki.ukim.mk.lab.model;

import lombok.Data;

@Data
public class Movie {
    private Long id;
    String title;
    String summary;
    private Production production;
    double rating;

    public Movie(String title, String summary, double rating, Production production) {
        this.id=(long) (Math.random() * 1000);
        this.production = production;
        this.title = title;
        this.summary = summary;
        this.rating = rating;
    }
}
