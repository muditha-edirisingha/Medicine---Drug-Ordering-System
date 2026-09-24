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
public class Promotion {

    private Integer promotionId;

    private String promotionName;
    private String description;

    private String discountType;
    private Double discountValue;

    private LocalDate startDate;
    private LocalDate endDate;

    private String status;

    private Integer marketingOfficerId;
}
