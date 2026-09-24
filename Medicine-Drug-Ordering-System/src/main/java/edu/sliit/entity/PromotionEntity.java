package edu.sliit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class PromotionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
