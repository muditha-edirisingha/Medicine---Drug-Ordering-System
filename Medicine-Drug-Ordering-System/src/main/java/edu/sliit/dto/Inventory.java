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
public class Inventory {
    private Integer inventoryId;
    private Integer medicineId;
    private Integer branchId;
    private Integer stockQuantity;
    private Integer reorderLevel;
    private LocalDateTime lastUpdated;
}

