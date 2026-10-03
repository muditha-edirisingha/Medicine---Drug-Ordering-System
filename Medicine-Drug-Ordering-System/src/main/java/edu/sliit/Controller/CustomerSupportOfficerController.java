package edu.sliit.Controller;

import edu.sliit.dto.CustomerSupportOfficer;
import edu.sliit.service.CustomerSupportOfficerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/customer-support-officer")
@RequiredArgsConstructor

public class CustomerSupportOfficerController {

    final CustomerSupportOfficerService service;


    @GetMapping("/get-all")
    public List<CustomerSupportOfficer> getAll() {
        return service.getCustomerSupportOfficers();
    }


    @GetMapping("/search-by-support-officer-id/{supportOfficerId}")
    public CustomerSupportOfficer searchBySupportOfficerId(
            @PathVariable Integer supportOfficerId) {

        return service.searchBySupportOfficerId(
                supportOfficerId
        );
    }


    @GetMapping("/search-by-first-name/{firstName}")
    public List<CustomerSupportOfficer> searchByFirstName(
            @PathVariable String firstName) {

        return service.searchByFirstName(firstName);
    }


    @GetMapping("/search-by-email/{email}")
    public List<CustomerSupportOfficer> searchByEmail(
            @PathVariable String email) {

        return service.searchByEmail(email);
    }


    @GetMapping("/search-by-status/{status}")
    public List<CustomerSupportOfficer> searchByStatus(
            @PathVariable String status) {

        return service.searchByStatus(status);
    }


    @PostMapping("/add")
    public String add(
            @RequestBody CustomerSupportOfficer supportOfficer) {

        service.addCustomerSupportOfficer(
                supportOfficer
        );

        return "Customer Support Officer added successfully";
    }


    @PutMapping("/update")
    public String update(
            @RequestBody CustomerSupportOfficer supportOfficer) {

        service.updateCustomerSupportOfficer(
                supportOfficer
        );

        return "Customer Support Officer updated successfully";
    }


    @DeleteMapping("/delete/{supportOfficerId}")
    public String delete(
            @PathVariable Integer supportOfficerId) {

        service.deleteBySupportOfficerId(
                supportOfficerId
        );

        return "Customer Support Officer deleted successfully";
    }
}