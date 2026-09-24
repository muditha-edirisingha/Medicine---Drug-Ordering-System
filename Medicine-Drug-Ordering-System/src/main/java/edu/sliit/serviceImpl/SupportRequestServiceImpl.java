package edu.sliit.serviceImpl;

import edu.sliit.dto.SupportRequest;
import edu.sliit.entity.SupportRequestEntity;
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
public class SupportRequestServiceImpl implements SupportRequestService {

    final SupportRequestRepository repository;
    final ModelMapper mapper;
    @Override
    public List<SupportRequest> getSupportRequests() {
        List<SupportRequest> supportRequests = new ArrayList<>();

        repository.findAll().forEach(request -> {

            supportRequests.add(
                    mapper.map(request, SupportRequest.class)
            );

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

        repository.save(
                mapper.map(
                        supportRequest,
                        SupportRequestEntity.class
                )
        );
    }

    @Override
    public SupportRequest searchBySupportId(Integer supportId) {
        SupportRequestEntity entity =
                repository.findById(supportId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Support request not found"
                                ));

        return mapper.map(entity, SupportRequest.class);
    }

    @Override
    public List<SupportRequest> searchByCustomerId(Integer customerId) {
        List<SupportRequest> supportRequests =
                new ArrayList<>();

        repository.findByCustomerId(customerId)
                .forEach(request -> {

                    supportRequests.add(
                            mapper.map(
                                    request,
                                    SupportRequest.class
                            )
                    );

                });

        return supportRequests;
    }

    @Override
    public List<SupportRequest> searchByStatus(String status) {
        List<SupportRequest> supportRequests =
                new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(request -> {

                    supportRequests.add(
                            mapper.map(
                                    request,
                                    SupportRequest.class
                            )
                    );

                });

        return supportRequests;
    }

    @Override
    public void updateSupportRequest(SupportRequest supportRequest) {
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

        repository.save(
                mapper.map(
                        supportRequest,
                        SupportRequestEntity.class
                )
        );
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
