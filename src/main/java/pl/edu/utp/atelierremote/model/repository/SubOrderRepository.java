package pl.edu.utp.atelierremote.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.utp.atelierremote.model.DishOrder;
import pl.edu.utp.atelierremote.model.SubOrder;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubOrderRepository extends JpaRepository<SubOrder, Long> {
    Optional<SubOrder> deleteByDishTypeId(Long id);
    List<SubOrder> findAllByDishTypeId(Long Id);
}
