package edu.sliit.repository;

import edu.sliit.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<MedicineEntity,Integer> {
}
