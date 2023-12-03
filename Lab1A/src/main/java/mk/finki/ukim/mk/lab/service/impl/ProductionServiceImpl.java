package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Production;
import mk.finki.ukim.mk.lab.repository.impl.ProductionRepository;
import mk.finki.ukim.mk.lab.repository.jpa.ProductionRepositoryInterface;
import mk.finki.ukim.mk.lab.service.ProductionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionServiceImpl  implements ProductionService {
    private final ProductionRepositoryInterface productionRepository;
    public ProductionServiceImpl(ProductionRepositoryInterface productionRepository){
        this.productionRepository=productionRepository;
    }
    @Override
    public List<Production> listAll() {
        return productionRepository.findAll();
    }

    @Override
    public Optional<Production> findById(Long id) {
        return productionRepository.findById(id);
    }

    @Override
    public Optional<Production> save(String name, String country, String address) {
       Production production=new Production(name,country,address);
        return Optional.of(productionRepository.save(production));
    }

    @Override
    public void deleteById(Long id) {
        productionRepository.deleteById(id);
    }

}
