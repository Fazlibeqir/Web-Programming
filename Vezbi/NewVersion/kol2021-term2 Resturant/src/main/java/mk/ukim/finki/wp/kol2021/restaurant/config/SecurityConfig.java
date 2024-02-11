package mk.ukim.finki.wp.kol2021.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests((auth)->auth
                       .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                       .anyRequest().authenticated()
               ).formLogin((login)->login.permitAll()
                       .failureUrl("/login?error=BadCredentials")
                       .defaultSuccessUrl("/menu",true)
               ).logout((logout)->logout
                       .logoutUrl("/logout")
                       .clearAuthentication(true)
                       .invalidateHttpSession(true)
                       .deleteCookies("JSESSIONID")
                       .logoutSuccessUrl("/")
               );
       return httpSecurity.build();
   }
   @Bean
    public UserDetailsService userDetailsService(){
       UserDetails userDetails1=User.builder()
               .username("user")
               .password(passwordEncoder.encode("user"))
               .roles("USER").build();

       UserDetails userDetails2=User.builder()
               .username("admin")
               .password(passwordEncoder.encode("admin"))
               .roles("ADMIN").build();
       return new InMemoryUserDetailsManager(userDetails1,userDetails2);
   }




}
