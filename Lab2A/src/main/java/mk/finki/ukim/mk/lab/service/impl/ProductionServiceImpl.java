package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Production;
import mk.finki.ukim.mk.lab.repository.ProductionRepository;
import mk.finki.ukim.mk.lab.service.ProductionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionServiceImpl  implements ProductionService {
    private final ProductionRepository productionRepository;
    public ProductionServiceImpl(ProductionRepository productionRepository){
        this.productionRepository=productionRepository;
    }
    @Override
    public List<Production> listAll() {
        return productionRepository.findAll();
    }

    @Override
    public Optional<Production> findByID(Long id) {
        return productionRepository.findByID(id);
    }

}
