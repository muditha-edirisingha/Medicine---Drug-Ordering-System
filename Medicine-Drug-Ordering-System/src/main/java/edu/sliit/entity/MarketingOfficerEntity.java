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
@Table(name = "marketing_officers")
public class MarketingOfficerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer marketingOfficerId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String username;
    private String password;
    private String status;
}