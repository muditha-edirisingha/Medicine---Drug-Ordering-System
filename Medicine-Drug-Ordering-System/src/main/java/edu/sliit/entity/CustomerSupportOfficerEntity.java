package edu.sliit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_support_officers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSupportOfficerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supportOfficerId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String username;
    private String password;
    private String status;
}