package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BranchManager {

    private Integer branchManagerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String username;
    private String password;
    private String status;
}