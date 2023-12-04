package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Production;

import java.util.List;
import java.util.Optional;

public interface ProductionService {
    List<Production> listAll();
    Optional<Production> findByID(Long id);
}
