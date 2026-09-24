package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Medicine {
    private Integer medicineId;

    private String medicineName;

    private String genericName;

    private String brandName;

    private String description;

    private String category;

    private String strength;

    private Double unitPrice;

    private LocalDate expiryDate;

    private String manufacturer;

    private Boolean prescriptionRequired;
}
