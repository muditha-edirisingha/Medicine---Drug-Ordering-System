package edu.sliit.repository;

import edu.sliit.entity.BranchManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchManagerRepository extends JpaRepository<BranchManagerEntity, Integer> {

    Optional<BranchManagerEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}