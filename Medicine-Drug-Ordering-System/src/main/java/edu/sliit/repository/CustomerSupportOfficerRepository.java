package edu.sliit.repository;

import edu.sliit.entity.CustomerSupportOfficerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerSupportOfficerRepository
        extends JpaRepository<CustomerSupportOfficerEntity, Integer> {

    List<CustomerSupportOfficerEntity>
    findByFirstNameContainingIgnoreCase(String firstName);

    List<CustomerSupportOfficerEntity>
    findByEmailContainingIgnoreCase(String email);

    List<CustomerSupportOfficerEntity>
    findByStatusIgnoreCase(String status);
}