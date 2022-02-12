package pl.edu.utp.atelierremote.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.utp.atelierremote.model.DishOrder;

import java.util.Optional;

@Repository
public interface DishOrderRepository extends JpaRepository<DishOrder, Long> {
}
