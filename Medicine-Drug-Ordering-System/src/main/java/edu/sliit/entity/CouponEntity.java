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
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponId;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;

    private String couponCode;

    private Integer usageLimit;
    private Integer usedCount;

    private String status;
}
