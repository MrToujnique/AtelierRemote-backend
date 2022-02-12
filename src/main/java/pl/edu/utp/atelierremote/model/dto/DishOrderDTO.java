package pl.edu.utp.atelierremote.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.utp.atelierremote.model.DishOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class DishOrderDTO {
    Long id;
    LocalDateTime issuedAt;
    CustomerDTO customer;
    List<SubOrderDTO> subOrders;

    public DishOrderDTO(DishOrder dishOrder) {
        this.id = dishOrder.getId();
        this.issuedAt = dishOrder.getIssuedAt();
        this.customer = new CustomerDTO(dishOrder.getCustomer());
        this.subOrders = dishOrder.getSubOrders().stream().map(SubOrderDTO::new).collect(Collectors.toList());
    }
}
