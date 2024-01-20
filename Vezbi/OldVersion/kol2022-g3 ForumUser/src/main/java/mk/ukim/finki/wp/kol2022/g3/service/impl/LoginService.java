package mk.ukim.finki.wp.kol2022.g3.service.impl;

import mk.ukim.finki.wp.kol2022.g3.model.ForumUser;
import mk.ukim.finki.wp.kol2022.g3.repository.ForumUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LoginService implements UserDetailsService {
    private final ForumUserRepository forumUserRepository;

    public LoginService(ForumUserRepository forumUserRepository) {
        this.forumUserRepository = forumUserRepository;
    }

    //TODO check this one more time
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ForumUser forumUser=this.forumUserRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User doesnt exist"));
        return new User(
                forumUser.getEmail(),
                forumUser.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_"+forumUser.getType())).collect(Collectors.toList())
        );
    }
}
