package edu.sliit.Controller;

import edu.sliit.dto.Customer;
import edu.sliit.dto.LoginRequest;
import edu.sliit.dto.LoginResponse;
import edu.sliit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import edu.sliit.dto.StaffRegisterRequest;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(
            @RequestBody Customer customer) {

        authService.registerCustomer(customer);
    }

    @PostMapping("/register/staff")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerStaff(
            @RequestBody StaffRegisterRequest request) {

        authService.registerStaff(request);
    }
}