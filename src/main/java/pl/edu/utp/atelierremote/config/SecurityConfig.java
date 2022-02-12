package pl.edu.utp.atelierremote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.edu.utp.atelierremote.filter.JwtFilter;
import pl.edu.utp.atelierremote.model.AppUser;
import pl.edu.utp.atelierremote.model.repository.AppUserRepository;
import pl.edu.utp.atelierremote.service.UserDetailsServiceImpl;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AppUserRepository repo;
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .headers().frameOptions().disable()
                .and()
                .cors()
                .and()
                .csrf().disable().authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/dishes").permitAll()
                .antMatchers("/api/freeReservationDates/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/dishes").permitAll()
                .antMatchers(HttpMethod.POST, "/api/orders").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tables").permitAll()
                .antMatchers(HttpMethod.POST,"/reservations").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/dishes").permitAll()
                .antMatchers(HttpMethod.GET,"/api/orders").permitAll()
                .antMatchers(HttpMethod.GET,"/reservations").permitAll()
                .antMatchers(HttpMethod.GET, "/api/report").permitAll()
                .antMatchers("/api/customers").hasAnyRole("COOK")
                .antMatchers("/api/customersTables").hasAnyRole("COOK")
                .and()
                .exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        AppUser appUser = new AppUser("chef", passwordEncoder().encode("chef123").toCharArray(), "chef@mail.pl", "COOK");
        repo.save(appUser);
    }
}