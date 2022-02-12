package pl.edu.utp.atelierremote.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.utp.atelierremote.model.WorkDay;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
}
