package pl.edu.utp.atelierremote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String login;
    private char[] password;
    private String email;
    private String role;

    public AppUser(String login, char[] password, String email, String role) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}