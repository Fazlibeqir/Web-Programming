package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidArgumentsException;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.PagedResultsControl;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "user-list";
    }
    @GetMapping("/add")
    public String getRegisterPage(@RequestParam(required = false) String error,
                                  Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "add-user";
    }
    @PostMapping("/add")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam LocalDate dateOfBirth) {
        try{
            this.userService.saveUser(username,name, surname,password,dateOfBirth);
            return "redirect:/users/list";
        } catch (InvalidArgumentsException exception) {
            return "redirect:/users/add?error=" + exception.getMessage();
        }
    }
}
