package edu.sliit.service;

import edu.sliit.dto.Customer;
import edu.sliit.dto.LoginRequest;
import edu.sliit.dto.LoginResponse;
import edu.sliit.dto.StaffRegisterRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);
    void registerCustomer(Customer customer);

    void registerStaff(StaffRegisterRequest request);
}