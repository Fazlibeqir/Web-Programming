package mk.ukim.finki.wp.kol2021.restaurant.web;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuItemService;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import net.sourceforge.htmlunit.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MenuController {

    private final MenuService service;
    private final MenuItemService menuItemService;

    public MenuController(MenuService service, MenuItemService menuItemService) {
        this.service = service;
        this.menuItemService = menuItemService;
    }


    @GetMapping({"/","/menu"})
    public String showMenus(@RequestParam(required = false) String nameSearch,
                            @RequestParam(required = false) MenuType menuType, Model model) {
        List<Menu> menus;
        if (nameSearch == null && menuType == null) {
            menus=service.listAll();
        } else {
           menus= this.service.listMenuItemsByRestaurantNameAndMenuType(nameSearch,  menuType);
        }
        model.addAttribute("menus",menus);
        model.addAttribute("types",MenuType.values());
        return "list";
    }

    @GetMapping("/menu/add")
    public String showAdd(Model model) {

        model.addAttribute("menuItems",this.menuItemService.listAll());
        model.addAttribute("types",MenuType.values());
        return "form";
    }

    @GetMapping("/menu/{id}/edit")
    public String showEdit(@PathVariable Long id,Model model) {

        model.addAttribute("menu",this.service.findById(id));
        model.addAttribute("menuItems",this.menuItemService.listAll());
        model.addAttribute("types",MenuType.values());
        return "form";
    }


    @PostMapping("/menu")
    public String create(@RequestParam String name,
                         @RequestParam MenuType menuType,
                         @RequestParam List<Long> menuItemIds) {
        this.service.create(name, menuType, menuItemIds);
        return "redirect:/menu";
    }

    @PostMapping("/menu/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam MenuType menuType,
                         @RequestParam List<Long> menuItemIds) {
        this.service.update(id, name, menuType, menuItemIds);
        return "redirect:/menu";
    }

    @PostMapping("/menu/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.service.delete(id);
        return "redirect:/menu";
    }
}
