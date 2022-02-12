package pl.edu.utp.atelierremote.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.utp.atelierremote.model.DinnerTable;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class DinnerTableDTO {
    Long id;
    Integer numberOfSeats;

    public DinnerTableDTO(DinnerTable dinnerTable) {
        this.id = dinnerTable.getId();
        this.numberOfSeats = dinnerTable.getNumberOfSeats();
    }
}