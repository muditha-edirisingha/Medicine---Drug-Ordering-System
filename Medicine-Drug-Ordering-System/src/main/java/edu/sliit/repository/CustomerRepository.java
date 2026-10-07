package edu.sliit.repository;

import edu.sliit.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    List<CustomerEntity> findByFirstNameContainingIgnoreCase(String firstName);

    List<CustomerEntity> findByEmailContainingIgnoreCase(String email);

    List<CustomerEntity> findByStatusIgnoreCase(String status);

    Optional<CustomerEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
