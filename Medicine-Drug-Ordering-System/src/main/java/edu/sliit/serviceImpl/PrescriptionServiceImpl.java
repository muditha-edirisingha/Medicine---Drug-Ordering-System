package edu.sliit.serviceImpl;

import edu.sliit.dto.Prescription;
import edu.sliit.entity.PrescriptionEntity;
import edu.sliit.repository.PrescriptionRepository;
import edu.sliit.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService{
    final PrescriptionRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Prescription> getPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();

        repository.findAll().forEach(prescription -> {

            prescriptions.add(
                    mapper.map(prescription, Prescription.class)
            );

        });

        return prescriptions;
    }

    @Override
    public void addPrescription(Prescription prescription) {
        if (prescription.getUploadDate() == null) {
            prescription.setUploadDate(LocalDateTime.now());
        }

        if (prescription.getStatus() == null) {
            prescription.setStatus("PENDING");
        }

        repository.save(
                mapper.map(prescription, PrescriptionEntity.class)
        );
    }

    @Override
    public Prescription searchByPrescriptionId(Integer prescriptionId) {
        PrescriptionEntity prescriptionEntity =
                repository.findById(prescriptionId)
                        .orElseThrow(() ->
                                new RuntimeException("Prescription not found"));

        return mapper.map(prescriptionEntity, Prescription.class);
    }

    @Override
    public List<Prescription> searchByCustomerId(Integer customerId) {
        List<Prescription> prescriptions = new ArrayList<>();

        repository.findByCustomerId(customerId)
                .forEach(prescription -> {

                    prescriptions.add(
                            mapper.map(prescription, Prescription.class)
                    );

                });

        return prescriptions;
    }

    @Override
    public List<Prescription> searchByStatus(String status) {
        List<Prescription> prescriptions = new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(prescription -> {

                    prescriptions.add(
                            mapper.map(prescription, Prescription.class)
                    );

                });

        return prescriptions;
    }

    @Override
    public void updatePrescription(Prescription prescription) {
        if (!repository.existsById(prescription.getPrescriptionId())) {
            throw new RuntimeException("Prescription not found");
        }

        if (prescription.getStatus() != null &&
                (prescription.getStatus().equalsIgnoreCase("APPROVED") ||
                        prescription.getStatus().equalsIgnoreCase("REJECTED"))) {

            prescription.setReviewedDate(LocalDateTime.now());
        }

        repository.save(
                mapper.map(prescription, PrescriptionEntity.class)
        );
    }

    @Override
    public void deleteByPrescriptionId(Integer prescriptionId) {
        if (!repository.existsById(prescriptionId)) {
            throw new RuntimeException("Prescription not found");
        }

        repository.deleteById(prescriptionId);
    }
}
