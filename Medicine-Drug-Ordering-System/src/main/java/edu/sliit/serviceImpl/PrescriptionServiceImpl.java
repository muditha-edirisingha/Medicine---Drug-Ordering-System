package edu.sliit.serviceImpl;

import edu.sliit.dto.Prescription;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.entity.PharmacistEntity;
import edu.sliit.entity.PrescriptionEntity;
import edu.sliit.exception.BadRequestException;
import edu.sliit.repository.CustomerRepository;
import edu.sliit.repository.PharmacistRepository;
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
public class PrescriptionServiceImpl implements PrescriptionService {

    final PrescriptionRepository repository;
    final ModelMapper mapper;
    final PharmacistRepository pharmacistRepository;
    final CustomerRepository customerRepository;

    @Override
    public List<Prescription> getPrescriptions() {

        List<Prescription> prescriptions = new ArrayList<>();

        repository.findAll().forEach(prescriptionEntity -> {

            Prescription prescription =
                    mapper.map(
                            prescriptionEntity,
                            Prescription.class
                    );

            if (prescriptionEntity.getCustomer() != null) {
                prescription.setCustomerId(
                        prescriptionEntity
                                .getCustomer()
                                .getCustomerId()
                );
            }

            if (prescriptionEntity.getPharmacist() != null) {
                prescription.setPharmacistId(
                        prescriptionEntity
                                .getPharmacist()
                                .getPharmacistId()
                );
            }

            prescriptions.add(prescription);
        });

        return prescriptions;
    }

    @Override
    public void addPrescription(Prescription prescription) {

        // Customer ID validation
        if (prescription.getCustomerId() == null) {
            throw new BadRequestException(
                    "Customer is required"
            );
        }

        // Check Customer exists
        CustomerEntity customer = customerRepository
                .findById(prescription.getCustomerId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Customer not found"
                        )
                );

        // Default upload date
        if (prescription.getUploadDate() == null) {
            prescription.setUploadDate(
                    LocalDateTime.now()
            );
        }

        // Default status
        if (prescription.getStatus() == null ||
                prescription.getStatus().trim().isEmpty()) {

            prescription.setStatus("PENDING");
        }

        // Status validation
        if (!isValidStatus(prescription.getStatus())) {
            throw new BadRequestException(
                    "Invalid prescription status. Allowed values: PENDING, APPROVED, REJECTED"
            );
        }

        // Rejection reason validation
        if (prescription.getStatus()
                .equalsIgnoreCase("REJECTED")) {

            if (prescription.getRejectionReason() == null ||
                    prescription.getRejectionReason()
                            .trim()
                            .isEmpty()) {

                throw new BadRequestException(
                        "Rejection reason is required when prescription is rejected"
                );
            }
        }

        PrescriptionEntity entity =
                mapper.map(
                        prescription,
                        PrescriptionEntity.class
                );

        entity.setCustomer(customer);

        // Pharmacist is optional when customer uploads prescription
        if (prescription.getPharmacistId() != null) {

            PharmacistEntity pharmacist =
                    pharmacistRepository
                            .findById(
                                    prescription.getPharmacistId()
                            )
                            .orElseThrow(() ->
                                    new BadRequestException(
                                            "Pharmacist not found"
                                    ));

            entity.setPharmacist(pharmacist);
        }

        // Approved / Rejected → reviewed date
        if (prescription.getStatus()
                .equalsIgnoreCase("APPROVED") ||
                prescription.getStatus()
                        .equalsIgnoreCase("REJECTED")) {

            entity.setReviewedDate(
                    LocalDateTime.now()
            );
        }

        repository.save(entity);
    }

    @Override
    public Prescription searchByPrescriptionId(
            Integer prescriptionId) {

        PrescriptionEntity prescriptionEntity =
                repository.findById(prescriptionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Prescription not found"
                                ));

        return mapper.map(
                prescriptionEntity,
                Prescription.class
        );
    }

    @Override
    public List<Prescription> searchByCustomerId(
            Integer customerId) {

        List<Prescription> prescriptions =
                new ArrayList<>();

        repository.findByCustomer_CustomerId(customerId)
                .forEach(prescription -> {

                    Prescription prescriptionDto =
                            mapper.map(
                                    prescription,
                                    Prescription.class
                            );

                    if (prescription.getCustomer() != null) {
                        prescriptionDto.setCustomerId(
                                prescription
                                        .getCustomer()
                                        .getCustomerId()
                        );
                    }

                    if (prescription.getPharmacist() != null) {
                        prescriptionDto.setPharmacistId(
                                prescription
                                        .getPharmacist()
                                        .getPharmacistId()
                        );
                    }

                    prescriptions.add(prescriptionDto);
                });

        return prescriptions;
    }

    @Override
    public List<Prescription> searchByStatus(
            String status) {

        List<Prescription> prescriptions =
                new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(prescription -> {

                    prescriptions.add(
                            mapper.map(
                                    prescription,
                                    Prescription.class
                            )
                    );

                });

        return prescriptions;
    }

    @Override
    public void updatePrescription(
            Prescription prescription) {

        // Prescription ID validation
        if (prescription.getPrescriptionId() == null) {
            throw new BadRequestException(
                    "Prescription ID is required"
            );
        }

        PrescriptionEntity entity =
                repository.findById(
                        prescription.getPrescriptionId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Prescription not found"
                        ));

        // Pharmacist validation
        if (prescription.getPharmacistId() != null) {

            PharmacistEntity pharmacist =
                    pharmacistRepository
                            .findById(
                                    prescription.getPharmacistId()
                            )
                            .orElseThrow(() ->
                                    new BadRequestException(
                                            "Pharmacist not found"
                                    ));

            entity.setPharmacist(pharmacist);
        }

        // Status validation
        if (prescription.getStatus() != null) {

            if (!isValidStatus(
                    prescription.getStatus()
            )) {
                throw new BadRequestException(
                        "Invalid prescription status. Allowed values: PENDING, APPROVED, REJECTED"
                );
            }

            // Rejection reason required
            if (prescription.getStatus()
                    .equalsIgnoreCase("REJECTED")) {

                if (prescription.getRejectionReason() == null ||
                        prescription.getRejectionReason()
                                .trim()
                                .isEmpty()) {

                    throw new BadRequestException(
                            "Rejection reason is required when prescription is rejected"
                    );
                }
            }

            entity.setStatus(
                    prescription.getStatus()
            );

            // Approved / Rejected → reviewed date
            if (prescription.getStatus()
                    .equalsIgnoreCase("APPROVED") ||
                    prescription.getStatus()
                            .equalsIgnoreCase("REJECTED")) {

                entity.setReviewedDate(
                        LocalDateTime.now()
                );
            }
        }

        if (prescription.getRejectionReason() != null) {
            entity.setRejectionReason(
                    prescription.getRejectionReason()
            );
        }

        repository.save(entity);
    }

    @Override
    public void deleteByPrescriptionId(
            Integer prescriptionId) {

        if (!repository.existsById(prescriptionId)) {
            throw new RuntimeException(
                    "Prescription not found"
            );
        }

        repository.deleteById(prescriptionId);
    }

    // Check valid prescription status
    private boolean isValidStatus(String status) {

        return status.equalsIgnoreCase("PENDING") ||
                status.equalsIgnoreCase("APPROVED") ||
                status.equalsIgnoreCase("REJECTED");
    }
}