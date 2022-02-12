package pl.edu.utp.atelierremote.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.utp.atelierremote.model.CustomerTable;

@NoArgsConstructor
@Getter
@Setter
public class CustomerTableDTO {
    Long id;

    String surname;
    String phoneNumber;

    public CustomerTableDTO(CustomerTable customerTable)
    {
        this.id = customerTable.getId();
        this.surname = customerTable.getSurname();
        this.phoneNumber = customerTable.getPhoneNumber();
    }
}
