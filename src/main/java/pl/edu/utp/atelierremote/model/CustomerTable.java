package pl.edu.utp.atelierremote.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CustomerTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String surname;
    String phoneNumber;

    @OneToMany(mappedBy = "customerTable")
    List<Reservation> reservations;

    public CustomerTable(String surname, String phoneNumber) {
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }
}
