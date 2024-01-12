package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Production;

import java.util.List;
import java.util.Optional;

public interface ProductionService {
    List<Production> listAll();
    Optional<Production> findById(Long id);
    Production save(String name,String country, String address);
    Optional<Production> edit(Long id,String name,String country,String address);
    void deleteById(Long id);
}
