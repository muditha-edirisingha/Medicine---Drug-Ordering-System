package edu.sliit.repository;

import edu.sliit.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryEntity,Integer> {
    List<InventoryEntity> findByMedicine_MedicineId(Integer medicineId);

    List<InventoryEntity> findByBranch_BranchId(Integer branchId);
}
