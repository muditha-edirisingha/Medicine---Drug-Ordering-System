package edu.sliit.serviceImpl;

import edu.sliit.dto.Medicine;
import edu.sliit.entity.MedicineEntity;
import edu.sliit.exception.BadRequestException;
import edu.sliit.repository.MedicineRepository;
import edu.sliit.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    final MedicineRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Medicine> getMedicine() {

        List<Medicine> medicines = new ArrayList<>();

        repository.findAll().forEach(medicine -> {

            medicines.add(
                    mapper.map(medicine, Medicine.class)
            );

        });

        return medicines;
    }

    @Override
    public void addMedicine(Medicine medicine) {

        // Medicine name validation
        if (medicine.getMedicineName() == null ||
                medicine.getMedicineName().trim().isEmpty()) {

            throw new BadRequestException(
                    "Medicine name is required"
            );
        }

        // Unit price validation
        if (medicine.getUnitPrice() == null ||
                medicine.getUnitPrice() <= 0) {

            throw new BadRequestException(
                    "Unit price must be greater than 0"
            );
        }

        // Expiry date validation
        if (medicine.getExpiryDate() == null ||
                !medicine.getExpiryDate().isAfter(LocalDate.now())) {

            throw new BadRequestException(
                    "Expiry date must be in the future"
            );
        }

        // Save medicine
        repository.save(
                mapper.map(medicine, MedicineEntity.class)
        );
    }

    @Override
    public Medicine searchByMedicineId(Integer medicineId) {

        MedicineEntity medicineEntity =
                repository.findById(medicineId)
                        .orElseThrow(() ->
                                new RuntimeException("Medicine not found"));

        return mapper.map(medicineEntity, Medicine.class);
    }

    @Override
    public void deleteByMedicineId(Integer medicineId) {

        if (!repository.existsById(medicineId)) {
            throw new RuntimeException("Medicine not found");
        }

        repository.deleteById(medicineId);
    }

    @Override
    public void updateMedicine(Medicine medicine) {

        if (!repository.existsById(medicine.getMedicineId())) {
            throw new RuntimeException("Medicine not found");
        }

        // Medicine name validation
        if (medicine.getMedicineName() == null ||
                medicine.getMedicineName().trim().isEmpty()) {

            throw new BadRequestException(
                    "Medicine name is required"
            );
        }

        // Unit price validation
        if (medicine.getUnitPrice() == null ||
                medicine.getUnitPrice() <= 0) {

            throw new BadRequestException(
                    "Unit price must be greater than 0"
            );
        }

        // Expiry date validation
        if (medicine.getExpiryDate() == null ||
                !medicine.getExpiryDate().isAfter(LocalDate.now())) {

            throw new BadRequestException(
                    "Expiry date must be in the future"
            );
        }

        // Update medicine
        repository.save(
                mapper.map(medicine, MedicineEntity.class)
        );
    }

    @Override
    public List<Medicine> searchByMedicineName(String medicineName) {

        List<Medicine> medicines = new ArrayList<>();

        repository.findAll().forEach(medicine -> {

            if (medicine.getMedicineName()
                    .toLowerCase()
                    .contains(medicineName.toLowerCase())) {

                medicines.add(
                        mapper.map(medicine, Medicine.class)
                );
            }

        });

        return medicines;
    }
}