package pl.edu.utp.atelierremote.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class SubOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    DishOrder order;

    @OneToOne
    Dish dishType;

    int quantity;

    public SubOrder(DishOrder mainOrder, Dish dishType, int quantity) {
        this.order = mainOrder;
        this.dishType = dishType;
        this.quantity = quantity;
    }
}
