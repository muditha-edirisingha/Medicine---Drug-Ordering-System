package edu.sliit.repository;

import edu.sliit.entity.MarketingOfficerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface MarketingOfficerRepository
        extends JpaRepository<MarketingOfficerEntity, Integer> {

    List<MarketingOfficerEntity>
    findByFirstNameContainingIgnoreCase(String firstName);

    List<MarketingOfficerEntity>
    findByEmailContainingIgnoreCase(String email);

    List<MarketingOfficerEntity>
    findByStatusIgnoreCase(String status);

    Optional<MarketingOfficerEntity> findByUsername(String username);
}