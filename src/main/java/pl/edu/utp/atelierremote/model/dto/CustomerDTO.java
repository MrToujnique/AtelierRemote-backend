package pl.edu.utp.atelierremote.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.utp.atelierremote.model.Customer;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    Long id;
    String phoneNumber;
    String address;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = customer.getAddress();
    }
}
