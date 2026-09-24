package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    private Integer medicineId;
    private Integer quantity;
    private Double unitPrice;
    private Double subTotal;
}
