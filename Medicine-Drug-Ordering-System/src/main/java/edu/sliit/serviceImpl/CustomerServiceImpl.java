package edu.sliit.serviceImpl;

import edu.sliit.dto.Customer;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.repository.CustomerRepository;
import edu.sliit.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import edu.sliit.exception.DuplicateResourceException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository repository;
    final ModelMapper mapper;
    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();

        repository.findAll().forEach(customerEntity -> {

            Customer customer =
                    mapper.map(customerEntity, Customer.class);

            customers.add(customer);
        });

        return customers;
    }

    @Override
    public Customer searchByCustomerId(Integer customerId) {

        CustomerEntity entity = repository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return mapper.map(entity, Customer.class);
    }

    @Override
    public List<Customer> searchByFirstName(String firstName) {
        List<Customer> customers = new ArrayList<>();

        repository.findByFirstNameContainingIgnoreCase(firstName)
                .forEach(customerEntity -> {

                    Customer customer =
                            mapper.map(customerEntity, Customer.class);

                    customers.add(customer);
                });

        return customers;
    }

    @Override
    public List<Customer> searchByEmail(String email) {
        List<Customer> customers = new ArrayList<>();

        repository.findByEmailContainingIgnoreCase(email)
                .forEach(customerEntity -> {

                    Customer customer =
                            mapper.map(customerEntity, Customer.class);

                    customers.add(customer);
                });

        return customers;
    }

    @Override
    public List<Customer> searchByStatus(String status) {
        List<Customer> customers = new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(customerEntity -> {

                    Customer customer =
                            mapper.map(customerEntity, Customer.class);

                    customers.add(customer);
                });

        return customers;
    }

    @Override
    public void addCustomer(Customer customer) {
        //User Name Validation
        if (customer.getUsername() == null ||
                customer.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }

        if (repository.existsByUsername(customer.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        //Email Valadation
        if (customer.getEmail() == null ||
                customer.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (!customer.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException("Invalid email format");
        }

        if (repository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        //Default set Active Status
        if (customer.getStatus() == null) {
            customer.setStatus("ACTIVE");
        }

        CustomerEntity entity =
                mapper.map(customer, CustomerEntity.class);

        entity.setStatus(customer.getStatus());

        repository.save(entity);
    }

    @Override
    public void updateCustomer(Customer customer) {
        CustomerEntity entity =
                mapper.map(customer, CustomerEntity.class);

        repository.save(entity);
    }

    @Override
    public void deleteByCustomerId(Integer customerId) {

        if (!repository.existsById(customerId)) {
            throw new RuntimeException("Customer not found");
        }

        repository.deleteById(customerId);
    }
}
