package edu.sliit.service;

import edu.sliit.dto.Prescription;

import java.util.List;

public interface PrescriptionService {
    List<Prescription> getPrescriptions();

    void addPrescription(Prescription prescription);

    Prescription searchByPrescriptionId(Integer prescriptionId);

    List<Prescription> searchByCustomerId(Integer customerId);

    List<Prescription> searchByStatus(String status);

    void updatePrescription(Prescription prescription);

    void deleteByPrescriptionId(Integer prescriptionId);
}
