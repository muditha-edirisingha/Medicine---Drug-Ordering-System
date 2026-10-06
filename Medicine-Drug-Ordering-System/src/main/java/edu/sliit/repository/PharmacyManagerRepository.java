package edu.sliit.repository;

import edu.sliit.entity.PharmacyManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PharmacyManagerRepository
        extends JpaRepository<PharmacyManagerEntity, Integer> {

    List<PharmacyManagerEntity>
    findByFirstNameContainingIgnoreCase(String firstName);

    List<PharmacyManagerEntity>
    findByEmailContainingIgnoreCase(String email);

    List<PharmacyManagerEntity>
    findByStatusIgnoreCase(String status);

    Optional<PharmacyManagerEntity> findByUsername(String username);
}