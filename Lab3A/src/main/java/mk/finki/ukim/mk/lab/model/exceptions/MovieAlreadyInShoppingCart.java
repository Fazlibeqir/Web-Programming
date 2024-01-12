package mk.finki.ukim.mk.lab.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class MovieAlreadyInShoppingCart extends RuntimeException{
    public MovieAlreadyInShoppingCart(Long id, String username) {
        super(String.format("Movie with id: %d already exists in shopping cart user with username %s",id,username));
    }
}
