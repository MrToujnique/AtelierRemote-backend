package pl.edu.utp.atelierremote.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate date;

    LocalTime startHour;
    LocalTime endHour;

    @ManyToOne
    CustomerTable customerTable;

    @ManyToOne
    DinnerTable dinnerTable;

    public Reservation(LocalDate date, LocalTime startHour, LocalTime endHour, CustomerTable customerTable, DinnerTable dinnerTable) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.customerTable = customerTable;
        this.dinnerTable = dinnerTable;
    }
}
