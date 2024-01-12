package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Production;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductionRepository {
    List<Production> productions;
    public ProductionRepository(){
        productions=new ArrayList<>();
        productions.add(new Production("Castle Rock Entertainment","USA","9169 West Sunset Boulevard"));
        productions.add(new Production("Paramount Pictures","USA","5555 Melrose Avenue, Hollywood, California"));
        productions.add(new Production("Marvel Studios","USA","500 South Buena Vista Street, Burbank, California"));
        productions.add(new Production("Warner Bros. Pictures","USA","4000 Warner Boulevard, Burbank, California"));
        productions.add(new Production("Marvel Studios","USA","500 South Buena Vista Street, Burbank, California"));
    }
    public List<Production> findAll(){
        return productions;
    }
    public Optional<Production> findByID(Long id){
        return productions.stream()
                .filter(production -> production.getId().equals(id))
                .findFirst();
    }
    public void deleteById(Long id){
        productions.removeIf(production -> production.getId().equals(id));
    }
}
