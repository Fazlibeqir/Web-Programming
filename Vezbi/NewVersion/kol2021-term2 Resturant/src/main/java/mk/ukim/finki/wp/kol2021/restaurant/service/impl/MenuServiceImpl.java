package mk.ukim.finki.wp.kol2021.restaurant.service.impl;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuItem;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.model.exceptions.InvalidMenuIdException;
import mk.ukim.finki.wp.kol2021.restaurant.repository.MenuRepository;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuItemService;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuItemService menuItemService;

    public MenuServiceImpl(MenuRepository menuRepository, MenuItemService menuItemService) {
        this.menuRepository = menuRepository;
        this.menuItemService = menuItemService;
    }

    @Override
    public List<Menu> listAll() {
        return this.menuRepository.findAll();
    }

    @Override
    public Menu findById(Long id) {
        return this.menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
    }

    @Override
    public Menu create(String restaurantName, MenuType menuType, List<Long> menuItems) {
        List<MenuItem> items=menuItems.stream().map(id->this.menuItemService.findById(id)).collect(Collectors.toList());
        Menu menu=new Menu(restaurantName,menuType,items);
        return this.menuRepository.save(menu);
    }

    @Override
    public Menu update(Long id, String restaurantName, MenuType menuType, List<Long> menuItems) {
       Menu menu=findById(id);
        List<MenuItem> items=menuItems.stream().map(ids->this.menuItemService.findById(ids)).collect(Collectors.toList());
        menu.setRestaurantName(restaurantName);
        menu.setMenuType(menuType);
        menu.setMenuItems(items);
        return this.menuRepository.save(menu);
    }

    @Override
    public Menu delete(Long id) {
        Menu menu=findById(id);
        this.menuRepository.delete(menu);
        return menu;
    }

    @Override
    public List<Menu> listMenuItemsByRestaurantNameAndMenuType(String restaurantName, MenuType menuType) {
        if (restaurantName==null && menuType==null){
            return listAll();
        }else if(restaurantName!=null && menuType!=null){
            return this.menuRepository.findByRestaurantNameContainsAndMenuType(restaurantName,menuType);
        }else if(restaurantName!=null){
            return this.menuRepository.findByRestaurantNameContains(restaurantName);
        }else{
            return this.menuRepository.findByMenuType(menuType);
        }
    }
}
