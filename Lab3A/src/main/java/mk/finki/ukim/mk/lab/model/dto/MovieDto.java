package mk.finki.ukim.mk.lab.model.dto;

import lombok.Data;
import mk.finki.ukim.mk.lab.model.Production;

@Data
public class MovieDto {
    String title;
    String summary;
    private Production production;
    double rating;
    public MovieDto() {
    }

    public MovieDto(String title, String summary, double rating, Production production) {
        this.production = production;
        this.title = title;
        this.summary = summary;
        this.rating = rating;
    }
}