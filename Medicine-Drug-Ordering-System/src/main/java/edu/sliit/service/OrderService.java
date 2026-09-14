package edu.sliit.service;

import edu.sliit.dto.Order;
import edu.sliit.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getOrder();
    void addOrder(Order order);

    void deleteByOrderId(Integer orderId);

    Optional<OrderEntity> searchByOrderId(Integer orderId);
}
