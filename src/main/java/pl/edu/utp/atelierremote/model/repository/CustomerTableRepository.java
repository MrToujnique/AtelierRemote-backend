package pl.edu.utp.atelierremote.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.utp.atelierremote.model.CustomerTable;

import java.util.Optional;

@Repository
public interface CustomerTableRepository extends JpaRepository<CustomerTable, Long> {
    Optional<CustomerTable> findByPhoneNumber(String phoneNumber);
}
