package pl.edu.utp.atelierremote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class DinnerTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer numberOfSeats;

    @OneToMany(mappedBy = "dinnerTable")
    List<Reservation> reservations;


    public DinnerTable(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
