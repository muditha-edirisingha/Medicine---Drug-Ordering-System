package edu.sliit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "pharmacists")
public class PharmacistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pharmacistId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String licenseNo;
    private String username;
    private String password;
    private String status;
}