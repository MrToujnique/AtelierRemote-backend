package pl.edu.utp.atelierremote.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.utp.atelierremote.model.Dish;
import pl.edu.utp.atelierremote.model.DishOrder;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}
