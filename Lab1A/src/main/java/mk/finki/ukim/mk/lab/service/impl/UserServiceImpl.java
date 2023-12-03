package mk.finki.ukim.mk.lab.service.impl;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.UserFullname;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepositoryInterface;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepositoryInterface userRepositoryInterface;

    public UserServiceImpl(UserRepositoryInterface userRepositoryInterface) {
        this.userRepositoryInterface = userRepositoryInterface;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepositoryInterface.findAll();
    }

    @Override
    public String getCurrentUsername() {
        HttpServletRequest request=((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String username=(String) request.getAttribute("username");

        Optional<User> userOptional = userRepositoryInterface.findByUsername(username);

        return userOptional.map(User::getUsername).orElse(null);

    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepositoryInterface.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepositoryInterface.findByUsername(username);
    }

    @Override
    public Optional<User> saveUser(String username,String name,String surname, String password , LocalDate dateOfBirth) {
        UserFullname fullname=new UserFullname();
        fullname.setName(name);
        fullname.setSurname(surname);
        User user=new User(username,fullname,password,dateOfBirth);
        return Optional.of(userRepositoryInterface.save(user));

    }

    @Override
    public void deleteUserById(Long id) {
        userRepositoryInterface.deleteById(id);
    }
}
