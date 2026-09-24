package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Prescription {
    private Integer prescriptionId;

    private Integer customerId;
    private Integer pharmacistId;

    private LocalDate prescriptionDate;
    private LocalDateTime uploadDate;

    private String prescriptionFile;
    private String status;

    private LocalDateTime reviewedDate;
    private String rejectionReason;
}
