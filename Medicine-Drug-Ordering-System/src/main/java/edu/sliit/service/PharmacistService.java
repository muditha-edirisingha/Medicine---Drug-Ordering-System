package edu.sliit.service;

import edu.sliit.dto.Pharmacist;

import java.util.List;

public interface PharmacistService {

    List<Pharmacist> getPharmacists();

    Pharmacist searchByPharmacistId(Integer pharmacistId);

    List<Pharmacist> searchByFirstName(String firstName);

    List<Pharmacist> searchByEmail(String email);

    List<Pharmacist> searchByStatus(String status);

    void addPharmacist(Pharmacist pharmacist);

    void updatePharmacist(Pharmacist pharmacist);

    void deleteByPharmacistId(Integer pharmacistId);
}