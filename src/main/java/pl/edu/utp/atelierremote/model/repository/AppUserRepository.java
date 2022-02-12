package pl.edu.utp.atelierremote.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.utp.atelierremote.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByLogin(String login);
}