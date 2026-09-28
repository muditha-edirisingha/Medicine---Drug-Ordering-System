package edu.sliit.service;

import edu.sliit.dto.CustomerSupportOfficer;

import java.util.List;

public interface CustomerSupportOfficerService {

    List<CustomerSupportOfficer> getCustomerSupportOfficers();

    CustomerSupportOfficer searchBySupportOfficerId(
            Integer supportOfficerId);

    List<CustomerSupportOfficer> searchByFirstName(
            String firstName);

    List<CustomerSupportOfficer> searchByEmail(
            String email);

    List<CustomerSupportOfficer> searchByStatus(
            String status);

    void addCustomerSupportOfficer(
            CustomerSupportOfficer supportOfficer);

    void updateCustomerSupportOfficer(
            CustomerSupportOfficer supportOfficer);

    void deleteBySupportOfficerId(
            Integer supportOfficerId);
}