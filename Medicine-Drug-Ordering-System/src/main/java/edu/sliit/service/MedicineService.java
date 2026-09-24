package edu.sliit.service;

import edu.sliit.dto.Medicine;

import java.util.List;

public interface MedicineService {
    List<Medicine> getMedicine();

    void addMedicine(Medicine medicine);

    Medicine searchByMedicineId(Integer medicineId);

    void deleteByMedicineId(Integer medicineId);

    void updateMedicine(Medicine medicine);

    List<Medicine> searchByMedicineName(String medicineName);
}
