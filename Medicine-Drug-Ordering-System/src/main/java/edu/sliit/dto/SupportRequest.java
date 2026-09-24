package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupportRequest {

    private Integer supportId;

    private Integer customerId;
    private Integer supportOfficerId;

    private String subject;
    private String description;

    private LocalDateTime requestDate;

    private String priority;
    private String status;

    private String resolution;
    private LocalDateTime resolvedDate;
}
