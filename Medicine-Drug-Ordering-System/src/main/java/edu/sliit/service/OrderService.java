package edu.sliit.service;

import edu.sliit.dto.Order;
import java.util.List;

public interface OrderService {
    List<Order> getOrder();
    void addOrder(Order order);

    void deleteByOrderId(Integer orderId);
}
