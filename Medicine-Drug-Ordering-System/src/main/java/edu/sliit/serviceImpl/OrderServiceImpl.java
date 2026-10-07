package edu.sliit.serviceImpl;

import edu.sliit.dto.Order;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.entity.OrderEntity;
import edu.sliit.exception.BadRequestException;
import edu.sliit.repository.CustomerRepository;
import edu.sliit.repository.OrderItemRepository;
import edu.sliit.repository.OrderRepository;
import edu.sliit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // Customer validation
        if (order.getCustomerId() == null) {
            throw new BadRequestException(
                    "Customer is required"
            );
        }

        CustomerEntity customer = customerRepository
                .findById(order.getCustomerId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Customer not found"
                        )
                );

        // Total amount validation
        if (order.getTotalAmount() == null ||
                order.getTotalAmount() < 0) {

            throw new BadRequestException(
                    "Total amount cannot be negative"
            );
        }

        // Order status validation
        if (order.getOrderStatus() == null ||
                !isValidOrderStatus(order.getOrderStatus())) {

            throw new BadRequestException(
                    "Invalid order status. Allowed values: PENDING, CONFIRMED, PROCESSING, COMPLETED"
            );
        }

        OrderEntity entity =
                mapper.map(order, OrderEntity.class);

        entity.setCustomer(customer);

        repository.save(entity);
    }

    @Override
    public void updateOrder(Order order) {

        if (order.getOrderId() == null) {
            throw new BadRequestException(
                    "Order ID is required"
            );
        }

        OrderEntity entity = repository.findById(order.getOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        // Customer validation
        if (order.getCustomerId() != null) {

            CustomerEntity customer = customerRepository
                    .findById(order.getCustomerId())
                    .orElseThrow(() ->
                            new BadRequestException(
                                    "Customer not found"
                            ));

            entity.setCustomer(customer);
        }

        if (order.getOrderDate() != null) {
            entity.setOrderDate(order.getOrderDate());
        }

        // Order status validation
        if (order.getOrderStatus() != null) {

            if (!isValidOrderStatus(order.getOrderStatus())) {
                throw new BadRequestException(
                        "Invalid order status. Allowed values: PENDING, CONFIRMED, PROCESSING, COMPLETED"
                );
            }

            entity.setOrderStatus(order.getOrderStatus());
        }

        // Total amount validation
        if (order.getTotalAmount() != null) {

            if (order.getTotalAmount() < 0) {
                throw new BadRequestException(
                        "Total amount cannot be negative"
                );
            }

            entity.setTotalAmount(order.getTotalAmount());
        }

        if (order.getDeliveryAddress() != null) {
            entity.setDeliveryAddress(order.getDeliveryAddress());
        }

        repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteByOrderId(Integer orderId) {

        if (!repository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }

        orderItemRepository.deleteByOrder_OrderId(orderId);

        repository.deleteById(orderId);
    }

    @Override
    public Optional<OrderEntity> searchByOrderId(Integer orderId) {
        return repository.findById(orderId);
    }

    @Override
    public List<Order> searchByCustomerId(Integer customerId) {

        List<Order> orders = new ArrayList<>();

        repository.findByCustomer_CustomerId(customerId)
                .forEach(orderEntity -> {

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

    // Check whether order status is valid
    private boolean isValidOrderStatus(String status) {

        return status.equalsIgnoreCase("PENDING") ||
                status.equalsIgnoreCase("CONFIRMED") ||
                status.equalsIgnoreCase("PROCESSING") ||
                status.equalsIgnoreCase("COMPLETED");
    }
}