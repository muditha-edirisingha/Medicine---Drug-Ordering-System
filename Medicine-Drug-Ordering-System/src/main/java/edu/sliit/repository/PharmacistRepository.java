package edu.sliit.repository;

import edu.sliit.entity.PharmacistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PharmacistRepository
        extends JpaRepository<PharmacistEntity, Integer> {

    List<PharmacistEntity> findByFirstNameContainingIgnoreCase(String firstName);

    List<PharmacistEntity> findByEmailContainingIgnoreCase(String email);

    List<PharmacistEntity> findByStatusIgnoreCase(String status);
}