package edu.sliit.entity;

import jakarta.persistence.Entity;
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
public class OrderEntity {
    private Integer orderId;
    private LocalDateTime orderdate;
    private String  orderStatus;

    private Double totalAmount;
    private String deliveryAddress;
}
