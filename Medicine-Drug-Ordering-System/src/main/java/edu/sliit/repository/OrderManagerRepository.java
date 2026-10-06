package edu.sliit.repository;

import edu.sliit.entity.OrderManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderManagerRepository extends JpaRepository<OrderManagerEntity, Integer> {

    Optional<OrderManagerEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}