package edu.sliit.serviceImpl;

import edu.sliit.dto.Order;
import edu.sliit.entity.OrderEntity;
import edu.sliit.repository.OrderItemRepository;
import edu.sliit.repository.OrderRepository;
import edu.sliit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    final OrderRepository repository;
    final OrderItemRepository orderItemRepository;
    final ModelMapper mapper;
    final CustomerRepository customerRepository;

    @Override
    public List<Order> getOrder() {

        List<Order> orders = new ArrayList<>();

        repository.findAll().forEach(orderEntity -> {

            Order order =
                    mapper.map(orderEntity, Order.class);

            if (orderEntity.getCustomer() != null) {
                order.setCustomerId(
                        orderEntity.getCustomer().getCustomerId()
                );
            }

            orders.add(order);
        });

        return orders;
    }

    @Override
    public void addOrder(Order order) {

        OrderEntity entity =
                mapper.map(order, OrderEntity.class);

        CustomerEntity customer = customerRepository
                .findById(order.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        entity.setCustomer(customer);

        repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteByOrderId(Integer orderId) {

        orderItemRepository.deleteByOrder_OrderId(orderId);

        repository.deleteById(orderId);
    }

    @Override
    public Optional<OrderEntity> searchByOrderId(Integer orderId) {
        return repository.findById(orderId);
    }
}
