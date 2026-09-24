package edu.sliit.repository;

import edu.sliit.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<BranchEntity , Integer> {
    List<BranchEntity> findByBranchNameContainingIgnoreCase(String branchName);
}
