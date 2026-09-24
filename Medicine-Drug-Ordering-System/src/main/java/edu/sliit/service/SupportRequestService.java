package edu.sliit.service;

import edu.sliit.dto.SupportRequest;

import java.util.List;

public interface SupportRequestService {

    List<SupportRequest> getSupportRequests();

    void addSupportRequest(SupportRequest supportRequest);

    SupportRequest searchBySupportId(Integer supportId);

    List<SupportRequest> searchByCustomerId(Integer customerId);

    List<SupportRequest> searchByStatus(String status);

    void updateSupportRequest(SupportRequest supportRequest);

    void deleteBySupportId(Integer supportId);
}
