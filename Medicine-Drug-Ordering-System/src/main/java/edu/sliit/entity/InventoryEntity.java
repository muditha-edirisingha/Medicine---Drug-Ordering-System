package edu.sliit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicine;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;
    private Integer stockQuantity;
    private Integer reorderLevel;
    private LocalDateTime lastUpdated;
}
