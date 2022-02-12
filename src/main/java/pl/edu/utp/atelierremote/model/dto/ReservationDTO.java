package pl.edu.utp.atelierremote.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.utp.atelierremote.model.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    Long id;

    LocalDate date;

    LocalTime startHour;
    LocalTime endHour;

    DinnerTableDTO dinnerTable;
    CustomerTableDTO customerTable;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.startHour = reservation.getStartHour();
        this.endHour = reservation.getEndHour();
        this.dinnerTable = new DinnerTableDTO(reservation.getDinnerTable());
        this.customerTable = new CustomerTableDTO(reservation.getCustomerTable());
    }
}
