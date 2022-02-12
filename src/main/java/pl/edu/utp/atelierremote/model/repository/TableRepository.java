package pl.edu.utp.atelierremote.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.utp.atelierremote.model.DinnerTable;

@Repository
public interface TableRepository extends JpaRepository<DinnerTable, Long> {
}
