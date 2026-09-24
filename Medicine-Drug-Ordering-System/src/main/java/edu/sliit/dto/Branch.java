package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Branch {

    private Integer branchId;
    private String branchName;
    private String phoneNo;
    private String email;
    private String openingTime;
    private String closingTime;
    private String status;
    private String address;
}
