package edu.sliit.service;

import edu.sliit.dto.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();

    Customer searchByCustomerId(Integer customerId);

    List<Customer> searchByFirstName(String firstName);

    List<Customer> searchByEmail(String email);

    List<Customer> searchByStatus(String status);

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteByCustomerId(Integer customerId);
}
