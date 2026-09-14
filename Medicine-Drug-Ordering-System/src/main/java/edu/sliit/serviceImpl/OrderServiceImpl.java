package edu.sliit.serviceImpl;

import edu.sliit.dto.Order;
import edu.sliit.entity.OrderEntity;
import edu.sliit.repository.OrderRepository;
import edu.sliit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    final OrderRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Order> getOrder() {
        List<Order> orders = new ArrayList<>();
        repository.findAll().forEach(order->{
            orders.add(mapper.map(order, Order.class));
        });
        return orders;
    }

    @Override
    public void addOrder(Order order) {
        repository.save(mapper.map(order, OrderEntity.class));
    }

    @Override
    public void deleteByOrderId(Integer orderId) {
        repository.deleteById(orderId);
    }
}
