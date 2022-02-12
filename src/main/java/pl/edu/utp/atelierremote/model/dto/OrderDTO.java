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
public class OrderDTO {
    LocalDateTime issuedAt;
    CustomerDTO customer;
    List<SubOrderDTO> subOrders;

    public OrderDTO(DishOrder order) {
        this.issuedAt = order.getIssuedAt();
        this.customer = new CustomerDTO(order.getCustomer());
        this.subOrders = order.getSubOrders().stream().map(SubOrderDTO::new).collect(Collectors.toList());
    }
}
