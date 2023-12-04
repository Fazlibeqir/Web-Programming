package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Production;
import mk.finki.ukim.mk.lab.service.ProductionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/list")
    public String listProductions(Model model) {
        List<Production> productions = productionService.listAll();
        model.addAttribute("productions", productions);
        return "production-list";
    }

    @GetMapping("/edit/{id}")
    public String editProductionForm(@PathVariable Long id,
                                     Model model) {
        if(this.productionService.findById(id).isPresent()) {
            Production production = productionService.findById(id).get();
            model.addAttribute("production", production);
            return "production-add";
        }
        return "redirect:/productions?error=ProductionNotFound";
    }

    @PostMapping("/edit/{id}")
    public String editProduction(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String country,
                                 @RequestParam String address) {
        if(id!=null){
            productionService.edit(id,name,country,address);
        }else{

            productionService.save(name,country,address);
        }
        return "redirect:/productions/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduction(@PathVariable Long id) {
        productionService.deleteById(id);
        return "redirect:/productions/list";
    }

    @GetMapping("/add")
    public String addProductionForm() {
        return "production-add";
    }

    @PostMapping("/add")
    public String addProduction(@RequestParam String name,
                                @RequestParam String country,
                                @RequestParam String address) {
        productionService.save(name, country, address);
        return "redirect:/productions/list";
    }
}
