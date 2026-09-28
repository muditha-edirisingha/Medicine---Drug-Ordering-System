package edu.sliit.serviceImpl;

import edu.sliit.dto.SupportRequest;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.entity.SupportRequestEntity;
import edu.sliit.repository.CustomerRepository;
import edu.sliit.repository.SupportRequestRepository;
import edu.sliit.service.SupportRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import edu.sliit.entity.CustomerSupportOfficerEntity;
import edu.sliit.repository.CustomerSupportOfficerRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportRequestServiceImpl implements SupportRequestService {

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
                    mapper.map(request, SupportRequest.class);

            if (request.getCustomer() != null) {
                dto.setCustomerId(
                        request.getCustomer().getCustomerId()
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
    public void addSupportRequest(SupportRequest supportRequest) {

        if (supportRequest.getRequestDate() == null) {
            supportRequest.setRequestDate(
                    LocalDateTime.now()
            );
        }

        if (supportRequest.getStatus() == null) {
            supportRequest.setStatus("OPEN");
        }

        if (supportRequest.getPriority() == null) {
            supportRequest.setPriority("NORMAL");
        }

        SupportRequestEntity entity =
                mapper.map(
                        supportRequest,
                        SupportRequestEntity.class
                );

        CustomerEntity customer =
                customerRepository.findById(
                        supportRequest.getCustomerId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found"
                        )
                );

        CustomerSupportOfficerEntity supportOfficer =
                customerSupportOfficerRepository.findById(
                        supportRequest.getSupportOfficerId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Customer support officer not found"
                        )
                );

        entity.setCustomer(customer);
        entity.setSupportOfficer(supportOfficer);

        repository.save(entity);
    }


    @Override
    public SupportRequest searchBySupportId(Integer supportId) {

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
                    entity.getCustomer().getCustomerId()
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

        if (!repository.existsById(
                supportRequest.getSupportId())) {

            throw new RuntimeException(
                    "Support request not found"
            );
        }

        if (supportRequest.getStatus() != null &&
                supportRequest.getStatus()
                        .equalsIgnoreCase("RESOLVED")) {

            supportRequest.setResolvedDate(
                    LocalDateTime.now()
            );
        }

        SupportRequestEntity entity =
                mapper.map(
                        supportRequest,
                        SupportRequestEntity.class
                );

        CustomerEntity customer =
                customerRepository.findById(
                        supportRequest.getCustomerId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found"
                        )
                );

        CustomerSupportOfficerEntity supportOfficer =
                customerSupportOfficerRepository.findById(
                        supportRequest.getSupportOfficerId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Customer support officer not found"
                        )
                );

        entity.setCustomer(customer);
        entity.setSupportOfficer(supportOfficer);

        repository.save(entity);
    }


    @Override
    public void deleteBySupportId(Integer supportId) {

        if (!repository.existsById(supportId)) {

            throw new RuntimeException(
                    "Support request not found"
            );
        }

        repository.deleteById(supportId);
    }
}