package pl.edu.utp.atelierremote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.utp.atelierremote.model.AppUser;
import pl.edu.utp.atelierremote.model.repository.AppUserRepository;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByLogin(username);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), String.valueOf(user.getPassword()), new ArrayList<>());
    }
}
