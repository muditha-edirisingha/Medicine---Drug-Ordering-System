package edu.sliit.service;

import edu.sliit.dto.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getOrderItems();
    void addOrderItem(OrderItem orderItem);
    OrderItem searchByOrderItemId(Integer orderItemId);
    void deleteByOrderItemId(Integer orderItemId);
    void updateOrderItem(OrderItem orderItem);
}
