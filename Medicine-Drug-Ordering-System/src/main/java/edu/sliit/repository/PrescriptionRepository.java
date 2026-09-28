package edu.sliit.repository;

import edu.sliit.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Integer> {
    List<PrescriptionEntity> findByCustomer_CustomerId(Integer customerId);

    List<PrescriptionEntity> findByPharmacist_PharmacistId(Integer pharmacistId);

    List<PrescriptionEntity> findByStatusIgnoreCase(String status);
}
