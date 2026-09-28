package edu.sliit.service;

import edu.sliit.dto.PharmacyManager;

import java.util.List;

public interface PharmacyManagerService {

    List<PharmacyManager> getPharmacyManagers();

    PharmacyManager searchByManagerId(Integer managerId);

    List<PharmacyManager> searchByFirstName(String firstName);

    List<PharmacyManager> searchByEmail(String email);

    List<PharmacyManager> searchByStatus(String status);

    void addPharmacyManager(PharmacyManager manager);

    void updatePharmacyManager(PharmacyManager manager);

    void deleteByManagerId(Integer managerId);
}