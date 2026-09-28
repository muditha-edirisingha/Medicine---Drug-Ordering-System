package edu.sliit.serviceImpl;

import edu.sliit.dto.Prescription;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.entity.PrescriptionEntity;
import edu.sliit.repository.CustomerRepository;
import edu.sliit.repository.PrescriptionRepository;
import edu.sliit.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import edu.sliit.entity.PharmacistEntity;
import edu.sliit.repository.PharmacistRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService{
    final PrescriptionRepository repository;
    final ModelMapper mapper;
    final PharmacistRepository pharmacistRepository;
    final CustomerRepository customerRepository;

    @Override
    public List<Prescription> getPrescriptions() {

        List<Prescription> prescriptions = new ArrayList<>();

        repository.findAll().forEach(prescriptionEntity -> {

            Prescription prescription =
                    mapper.map(prescriptionEntity, Prescription.class);

            if (prescriptionEntity.getCustomer() != null) {
                prescription.setCustomerId(
                        prescriptionEntity.getCustomer().getCustomerId()
                );
            }

            if (prescriptionEntity.getPharmacist() != null) {
                prescription.setPharmacistId(
                        prescriptionEntity.getPharmacist().getPharmacistId()
                );
            }

            prescriptions.add(prescription);
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

        PrescriptionEntity entity =
                mapper.map(prescription, PrescriptionEntity.class);

        CustomerEntity customer = customerRepository
                .findById(prescription.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        PharmacistEntity pharmacist = pharmacistRepository
                .findById(prescription.getPharmacistId())
                .orElseThrow(() ->
                        new RuntimeException("Pharmacist not found"));

        entity.setCustomer(customer);
        entity.setPharmacist(pharmacist);

        repository.save(entity);
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

        repository.findByCustomer_CustomerId(customerId)
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
