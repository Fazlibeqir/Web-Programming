package mk.ukim.finki.wp.june2022.g1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * This class is used to configure user login on path '/login' and logout on path '/logout'.
 * The only public page in the application should be '/'.
 * All other pages should be visible only for the authenticated users.
 * Furthermore, in the "list.html" template, the 'Edit', 'Delete', 'Add' buttons should only be
 * visible for a user with role 'ROLE_SYSADMIN'.
 * <p>
 * For login the users from the database should be used.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth)->
                        auth
                                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/VirtualServers")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/VirtualServers/{id}/terminate")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/VirtualServers/**")).hasRole("SYSADMIN")
                                .anyRequest().
                                authenticated()
                )
                .formLogin((from)->
                        from.permitAll()
                                .failureUrl("/login?error=BadCredentials")
                                .defaultSuccessUrl("/VirtualServers",true)
                ).logout((logout)->
                        logout.logoutUrl("/logout")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/"));
        return http.build();

    }
    @Bean
    public AuthenticationManager manager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder=http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(this.userDetailsService);
        return authBuilder.build();
    }



}
