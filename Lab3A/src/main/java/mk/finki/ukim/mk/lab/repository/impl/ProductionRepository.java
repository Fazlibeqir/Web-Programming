package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Production;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductionRepository {
//    List<Production> productions;
//    public ProductionRepository(){
//        productions=new ArrayList<>();
//        productions.add(new Production("Castle Rock Entertainment","USA","9169 West Sunset Boulevard"));
//        productions.add(new Production("Paramount Pictures","USA","5555 Melrose Avenue, Hollywood, California"));
//        productions.add(new Production("Marvel Studios","USA","500 South Buena Vista Street, Burbank, California"));
//        productions.add(new Production("Warner Bros. Pictures","USA","4000 Warner Boulevard, Burbank, California"));
//        productions.add(new Production("Marvel Studios","USA","500 South Buena Vista Street, Burbank, California"));
//    }
    public List<Production> findAll(){
        return DataHolder.productions;
    }
    public Optional<Production> findByID(Long id){
        return DataHolder.productions.stream()
                .filter(production -> production.getId().equals(id))
                .findFirst();
    }
    public Optional<Production> save(String name,String country,String address){
        Production production=new Production(name,country,address);
        DataHolder.productions.add(production);
        return Optional.of(production);
    }
    public void deleteById(Long id){
        DataHolder.productions.removeIf(production -> production.getId().equals(id));
    }
}
