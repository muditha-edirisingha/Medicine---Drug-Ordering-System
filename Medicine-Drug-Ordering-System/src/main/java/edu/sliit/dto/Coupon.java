package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Coupon {
    private Integer couponId;

    private Integer promotionId;

    private String couponCode;

    private Integer usageLimit;
    private Integer usedCount;

    private String status;
}
