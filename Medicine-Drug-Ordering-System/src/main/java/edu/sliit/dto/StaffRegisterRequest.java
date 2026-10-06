package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;

    // Only required for Pharmacist
    private String licenseNo;

    private String username;
    private String password;

    /*
     * PHARMACIST
     * PHARMACY_MANAGER
     * MARKETING_OFFICER
     * CUSTOMER_SUPPORT_OFFICER
     */
    private String role;
}