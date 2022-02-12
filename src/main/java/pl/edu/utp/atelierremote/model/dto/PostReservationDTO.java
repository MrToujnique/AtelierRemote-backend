package pl.edu.utp.atelierremote.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.utp.atelierremote.model.DinnerTable;
import pl.edu.utp.atelierremote.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReservationDTO {
    Long id;
    LocalDate actualDate;
    LocalTime actualHour;
    String phoneNumber;
    String surname;

    public PostReservationDTO(Reservation reservation)
    {
        this.id = reservation.getDinnerTable().getId();
        this.actualDate = reservation.getDate();
        this.actualHour = reservation.getStartHour();
        this.phoneNumber = reservation.getCustomerTable().getPhoneNumber();
        this.surname = reservation.getCustomerTable().getSurname();
    }
}
