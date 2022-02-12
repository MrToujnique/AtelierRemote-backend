package pl.edu.utp.atelierremote.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DishOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime issuedAt;

    @ManyToOne
    Customer customer;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<SubOrder> subOrders;

    public DishOrder(Customer customer) {
        this.issuedAt = LocalDateTime.now();
        this.customer = customer;
    }
}
