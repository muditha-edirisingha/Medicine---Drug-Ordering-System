package edu.sliit.Controller;

import edu.sliit.dto.SupportRequest;
import edu.sliit.service.SupportRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/support")
public class SupportRequestController {

    final SupportRequestService service;

    @GetMapping("/get-all")
    public List<SupportRequest> getSupportRequests() {

        return service.getSupportRequests();
    }

    @GetMapping("/search-by-id/{supportId}")
    public SupportRequest searchBySupportId(
            @PathVariable Integer supportId) {

        return service.searchBySupportId(supportId);
    }

    @GetMapping("/search-by-customer-id/{customerId}")
    public List<SupportRequest> searchByCustomerId(
            @PathVariable Integer customerId) {

        return service.searchByCustomerId(customerId);
    }

    @GetMapping("/search-by-status/{status}")
    public List<SupportRequest> searchByStatus(
            @PathVariable String status) {

        return service.searchByStatus(status);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSupportRequest(@RequestBody SupportRequest supportRequest) {

        service.addSupportRequest(supportRequest);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateSupportRequest(
            @RequestBody SupportRequest supportRequest) {

        service.updateSupportRequest(supportRequest);
    }

    @DeleteMapping("/delete/{supportId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteSupportRequest(
            @PathVariable Integer supportId) {

        service.deleteBySupportId(supportId);
    }
}
