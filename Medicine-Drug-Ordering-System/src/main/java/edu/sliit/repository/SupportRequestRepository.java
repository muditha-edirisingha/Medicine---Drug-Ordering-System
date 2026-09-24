package edu.sliit.repository;

import edu.sliit.entity.SupportRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRequestRepository extends JpaRepository<SupportRequestEntity, Integer> {
    List<SupportRequestEntity> findByCustomerId(Integer customerId);

    List<SupportRequestEntity> findByStatusIgnoreCase(String status);
}
