package edu.sliit.Controller;

import edu.sliit.dto.Customer;
import edu.sliit.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    final CustomerService service;

    @GetMapping("/get-all")
    public List<Customer> getCustomers() {
        return service.getCustomers();
    }

    @GetMapping("/search-by-customer-id/{customerId}")
    public Customer searchByCustomerId(
            @PathVariable Integer customerId) {

        return service.searchByCustomerId(customerId);
    }

    @GetMapping("/search-by-first-name/{firstName}")
    public List<Customer> searchByFirstName(
            @PathVariable String firstName) {

        return service.searchByFirstName(firstName);
    }

    @GetMapping("/search-by-email/{email}")
    public List<Customer> searchByEmail(
            @PathVariable String email) {

        return service.searchByEmail(email);
    }

    @GetMapping("/search-by-status/{status}")
    public List<Customer> searchByStatus(
            @PathVariable String status) {

        return service.searchByStatus(status);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(
            @RequestBody Customer customer) {

        service.addCustomer(customer);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(
            @RequestBody Customer customer) {

        service.updateCustomer(customer);
    }

    @DeleteMapping("/delete/{customerId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCustomer(
            @PathVariable Integer customerId) {

        service.deleteByCustomerId(customerId);
    }
}
