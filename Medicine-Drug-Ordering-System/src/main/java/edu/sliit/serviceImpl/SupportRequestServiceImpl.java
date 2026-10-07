package edu.sliit.serviceImpl;

import edu.sliit.dto.SupportRequest;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.entity.CustomerSupportOfficerEntity;
import edu.sliit.entity.SupportRequestEntity;
import edu.sliit.exception.BadRequestException;
import edu.sliit.repository.CustomerRepository;
import edu.sliit.repository.CustomerSupportOfficerRepository;
import edu.sliit.repository.SupportRequestRepository;
import edu.sliit.service.SupportRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportRequestServiceImpl
        implements SupportRequestService {

    final SupportRequestRepository repository;
    final CustomerRepository customerRepository;
    final CustomerSupportOfficerRepository customerSupportOfficerRepository;
    final ModelMapper mapper;

    @Override
    public List<SupportRequest> getSupportRequests() {

        List<SupportRequest> supportRequests =
                new ArrayList<>();

        repository.findAll().forEach(request -> {

            SupportRequest dto =
                    mapper.map(
                            request,
                            SupportRequest.class
                    );

            if (request.getCustomer() != null) {
                dto.setCustomerId(
                        request.getCustomer()
                                .getCustomerId()
                );
            }

            if (request.getSupportOfficer() != null) {
                dto.setSupportOfficerId(
                        request.getSupportOfficer()
                                .getSupportOfficerId()
                );
            }

            supportRequests.add(dto);
        });

        return supportRequests;
    }

    @Override
    public void addSupportRequest(
            SupportRequest supportRequest) {

        // Customer ID validation
        if (supportRequest.getCustomerId() == null) {
            throw new BadRequestException(
                    "Customer is required"
            );
        }

        // Customer existence validation
        CustomerEntity customer =
                customerRepository.findById(
                        supportRequest.getCustomerId()
                ).orElseThrow(() ->
                        new BadRequestException(
                                "Customer not found"
                        )
                );

        // Subject validation
        if (supportRequest.getSubject() == null ||
                supportRequest.getSubject()
                        .trim()
                        .isEmpty()) {

            throw new BadRequestException(
                    "Subject is required"
            );
        }

        // Description validation
        if (supportRequest.getDescription() == null ||
                supportRequest.getDescription()
                        .trim()
                        .isEmpty()) {

            throw new BadRequestException(
                    "Description is required"
            );
        }

        // Default request date
        if (supportRequest.getRequestDate() == null) {
            supportRequest.setRequestDate(
                    LocalDateTime.now()
            );
        }

        // Default status
        if (supportRequest.getStatus() == null ||
                supportRequest.getStatus()
                        .trim()
                        .isEmpty()) {

            supportRequest.setStatus("OPEN");
        }

        // Default priority
        if (supportRequest.getPriority() == null ||
                supportRequest.getPriority()
                        .trim()
                        .isEmpty()) {

            supportRequest.setPriority("NORMAL");
        }

        // Status validation
        if (!isValidStatus(
                supportRequest.getStatus())) {

            throw new BadRequestException(
                    "Invalid support request status. Allowed values: OPEN, IN_PROGRESS, RESOLVED"
            );
        }

        // Priority validation
        if (!isValidPriority(
                supportRequest.getPriority())) {

            throw new BadRequestException(
                    "Invalid priority. Allowed values: LOW, NORMAL, HIGH"
            );
        }

        // Resolution validation
        if (supportRequest.getStatus()
                .equalsIgnoreCase("RESOLVED")) {

            if (supportRequest.getResolution() == null ||
                    supportRequest.getResolution()
                            .trim()
                            .isEmpty()) {

                throw new BadRequestException(
                        "Resolution is required when support request is resolved"
                );
            }

            supportRequest.setResolvedDate(
                    LocalDateTime.now()
            );
        }

        SupportRequestEntity entity =
                mapper.map(
                        supportRequest,
                        SupportRequestEntity.class
                );

        entity.setCustomer(customer);

        // Support officer is optional when customer
        // creates a support request
        if (supportRequest.getSupportOfficerId() != null) {

            CustomerSupportOfficerEntity supportOfficer =
                    customerSupportOfficerRepository
                            .findById(
                                    supportRequest
                                            .getSupportOfficerId()
                            )
                            .orElseThrow(() ->
                                    new BadRequestException(
                                            "Customer support officer not found"
                                    )
                            );

            entity.setSupportOfficer(
                    supportOfficer
            );
        }

        repository.save(entity);
    }

    @Override
    public SupportRequest searchBySupportId(
            Integer supportId) {

        SupportRequestEntity entity =
                repository.findById(supportId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Support request not found"
                                ));

        SupportRequest dto =
                mapper.map(
                        entity,
                        SupportRequest.class
                );

        if (entity.getCustomer() != null) {
            dto.setCustomerId(
                    entity.getCustomer()
                            .getCustomerId()
            );
        }

        if (entity.getSupportOfficer() != null) {
            dto.setSupportOfficerId(
                    entity.getSupportOfficer()
                            .getSupportOfficerId()
            );
        }

        return dto;
    }

    @Override
    public List<SupportRequest> searchByCustomerId(
            Integer customerId) {

        List<SupportRequest> supportRequests =
                new ArrayList<>();

        repository.findByCustomer_CustomerId(customerId)
                .forEach(request -> {

                    SupportRequest dto =
                            mapper.map(
                                    request,
                                    SupportRequest.class
                            );

                    if (request.getCustomer() != null) {
                        dto.setCustomerId(
                                request.getCustomer()
                                        .getCustomerId()
                        );
                    }

                    if (request.getSupportOfficer() != null) {
                        dto.setSupportOfficerId(
                                request.getSupportOfficer()
                                        .getSupportOfficerId()
                        );
                    }

                    supportRequests.add(dto);
                });

        return supportRequests;
    }

    @Override
    public List<SupportRequest> searchByStatus(
            String status) {

        List<SupportRequest> supportRequests =
                new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(request -> {

                    SupportRequest dto =
                            mapper.map(
                                    request,
                                    SupportRequest.class
                            );

                    if (request.getCustomer() != null) {
                        dto.setCustomerId(
                                request.getCustomer()
                                        .getCustomerId()
                        );
                    }

                    if (request.getSupportOfficer() != null) {
                        dto.setSupportOfficerId(
                                request.getSupportOfficer()
                                        .getSupportOfficerId()
                        );
                    }

                    supportRequests.add(dto);
                });

        return supportRequests;
    }

    @Override
    public void updateSupportRequest(
            SupportRequest supportRequest) {

        // Support ID validation
        if (supportRequest.getSupportId() == null) {
            throw new BadRequestException(
                    "Support request ID is required"
            );
        }

        // Check support request exists
        SupportRequestEntity entity =
                repository.findById(
                        supportRequest.getSupportId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Support request not found"
                        ));

        // Customer validation
        if (supportRequest.getCustomerId() == null) {
            throw new BadRequestException(
                    "Customer is required"
            );
        }

        CustomerEntity customer =
                customerRepository.findById(
                        supportRequest.getCustomerId()
                ).orElseThrow(() ->
                        new BadRequestException(
                                "Customer not found"
                        )
                );

        // Subject validation
        if (supportRequest.getSubject() == null ||
                supportRequest.getSubject()
                        .trim()
                        .isEmpty()) {

            throw new BadRequestException(
                    "Subject is required"
            );
        }

        // Description validation
        if (supportRequest.getDescription() == null ||
                supportRequest.getDescription()
                        .trim()
                        .isEmpty()) {

            throw new BadRequestException(
                    "Description is required"
            );
        }

        // Status validation
        if (supportRequest.getStatus() == null ||
                !isValidStatus(
                        supportRequest.getStatus())) {

            throw new BadRequestException(
                    "Invalid support request status. Allowed values: OPEN, IN_PROGRESS, RESOLVED"
            );
        }

        // Priority validation
        if (supportRequest.getPriority() == null ||
                !isValidPriority(
                        supportRequest.getPriority())) {

            throw new BadRequestException(
                    "Invalid priority. Allowed values: LOW, NORMAL, HIGH"
            );
        }

        // Support officer is required when updating
        if (supportRequest.getSupportOfficerId() == null) {
            throw new BadRequestException(
                    "Customer support officer is required"
            );
        }

        CustomerSupportOfficerEntity supportOfficer =
                customerSupportOfficerRepository
                        .findById(
                                supportRequest
                                        .getSupportOfficerId()
                        )
                        .orElseThrow(() ->
                                new BadRequestException(
                                        "Customer support officer not found"
                                )
                        );

        // RESOLVED → resolution required
        if (supportRequest.getStatus()
                .equalsIgnoreCase("RESOLVED")) {

            if (supportRequest.getResolution() == null ||
                    supportRequest.getResolution()
                            .trim()
                            .isEmpty()) {

                throw new BadRequestException(
                        "Resolution is required when support request is resolved"
                );
            }

            supportRequest.setResolvedDate(
                    LocalDateTime.now()
            );
        }

        // Update fields
        entity.setCustomer(customer);
        entity.setSupportOfficer(supportOfficer);
        entity.setSubject(
                supportRequest.getSubject()
        );
        entity.setDescription(
                supportRequest.getDescription()
        );
        entity.setPriority(
                supportRequest.getPriority()
        );
        entity.setStatus(
                supportRequest.getStatus()
        );

        if (supportRequest.getResolution() != null) {
            entity.setResolution(
                    supportRequest.getResolution()
            );
        }

        if (supportRequest.getRequestDate() != null) {
            entity.setRequestDate(
                    supportRequest.getRequestDate()
            );
        }

        if (supportRequest.getStatus()
                .equalsIgnoreCase("RESOLVED")) {

            entity.setResolvedDate(
                    supportRequest.getResolvedDate()
            );
        }

        repository.save(entity);
    }

    @Override
    public void deleteBySupportId(
            Integer supportId) {

        if (!repository.existsById(supportId)) {
            throw new RuntimeException(
                    "Support request not found"
            );
        }

        repository.deleteById(supportId);
    }

    // Valid support request statuses
    private boolean isValidStatus(String status) {

        return status.equalsIgnoreCase("OPEN") ||
                status.equalsIgnoreCase("IN_PROGRESS") ||
                status.equalsIgnoreCase("RESOLVED");
    }

    // Valid support request priorities
    private boolean isValidPriority(String priority) {

        return priority.equalsIgnoreCase("LOW") ||
                priority.equalsIgnoreCase("NORMAL") ||
                priority.equalsIgnoreCase("HIGH");
    }
}