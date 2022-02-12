package pl.edu.utp.atelierremote.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.utp.atelierremote.model.Dish;
import pl.edu.utp.atelierremote.model.SubOrder;

@NoArgsConstructor
@Getter
@Setter
public class SubOrderDTO {
    Dish dishType;
    int quantity;

    public SubOrderDTO(SubOrder subOrder) {
        this.dishType = subOrder.getDishType();
        this.quantity = subOrder.getQuantity();
    }
}
