package edu.sliit.serviceImpl;

import edu.sliit.dto.OrderItem;
import edu.sliit.entity.MedicineEntity;
import edu.sliit.entity.OrderEntity;
import edu.sliit.entity.OrderItemEntity;
import edu.sliit.exception.BadRequestException;
import edu.sliit.repository.MedicineRepository;
import edu.sliit.repository.OrderItemRepository;
import edu.sliit.repository.OrderRepository;
import edu.sliit.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    final OrderItemRepository repository;
    final OrderRepository orderRepository;
    final MedicineRepository medicineRepository;
    final ModelMapper mapper;

    @Override
    public List<OrderItem> getOrderItems() {

        List<OrderItem> orderItems = new ArrayList<>();

        repository.findAll().forEach(orderItem -> {

            OrderItem item = new OrderItem();

            item.setOrderItemId(orderItem.getOrderItemId());

            if (orderItem.getMedicine() != null) {
                item.setMedicineId(
                        orderItem.getMedicine().getMedicineId()
                );
            }

            item.setQuantity(orderItem.getQuantity());
            item.setUnitPrice(orderItem.getUnitPrice());
            item.setSubTotal(orderItem.getSubTotal());

            if (orderItem.getOrder() != null) {
                item.setOrderId(
                        orderItem.getOrder().getOrderId()
                );
            }

            orderItems.add(item);
        });

        return orderItems;
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {

        // Order ID validation
        if (orderItem.getOrderId() == null) {
            throw new BadRequestException(
                    "Order is required"
            );
        }

        // Check Order exists
        OrderEntity order = orderRepository
                .findById(orderItem.getOrderId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Order not found"
                        )
                );

        // Medicine ID validation
        if (orderItem.getMedicineId() == null) {
            throw new BadRequestException(
                    "Medicine is required"
            );
        }

        // Check Medicine exists
        MedicineEntity medicine = medicineRepository
                .findById(orderItem.getMedicineId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Medicine not found"
                        )
                );

        // Quantity validation
        if (orderItem.getQuantity() == null ||
                orderItem.getQuantity() <= 0) {

            throw new BadRequestException(
                    "Quantity must be greater than 0"
            );
        }

        // Unit price validation
        if (orderItem.getUnitPrice() == null ||
                orderItem.getUnitPrice() <= 0) {

            throw new BadRequestException(
                    "Unit price must be greater than 0"
            );
        }

        // Subtotal validation
        if (orderItem.getSubTotal() == null) {
            throw new BadRequestException(
                    "Subtotal is required"
            );
        }

        double expectedSubTotal =
                orderItem.getQuantity() *
                        orderItem.getUnitPrice();

        if (Math.abs(
                orderItem.getSubTotal() - expectedSubTotal
        ) > 0.01) {

            throw new BadRequestException(
                    "Subtotal must equal quantity multiplied by unit price"
            );
        }

        OrderItemEntity entity =
                mapper.map(orderItem, OrderItemEntity.class);

        // Set Order relationship
        entity.setOrder(order);

        // Set Medicine relationship
        entity.setMedicine(medicine);

        repository.save(entity);
    }

    @Override
    public OrderItem searchByOrderItemId(Integer orderItemId) {

        OrderItemEntity entity =
                repository.findById(orderItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order Item not found"
                                ));

        return mapper.map(entity, OrderItem.class);
    }

    @Override
    public void deleteByOrderItemId(Integer orderItemId) {

        if (!repository.existsById(orderItemId)) {
            throw new RuntimeException(
                    "Order Item not found"
            );
        }

        repository.deleteById(orderItemId);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {

        if (orderItem.getOrderItemId() == null) {
            throw new BadRequestException(
                    "Order Item ID is required"
            );
        }

        // Check existing Order Item
        OrderItemEntity entity =
                repository.findById(
                        orderItem.getOrderItemId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Order Item not found"
                        ));

        // Order validation
        if (orderItem.getOrderId() == null) {
            throw new BadRequestException(
                    "Order is required"
            );
        }

        OrderEntity order = orderRepository
                .findById(orderItem.getOrderId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Order not found"
                        )
                );

        // Medicine validation
        if (orderItem.getMedicineId() == null) {
            throw new BadRequestException(
                    "Medicine is required"
            );
        }

        MedicineEntity medicine = medicineRepository
                .findById(orderItem.getMedicineId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Medicine not found"
                        )
                );

        // Quantity validation
        if (orderItem.getQuantity() == null ||
                orderItem.getQuantity() <= 0) {

            throw new BadRequestException(
                    "Quantity must be greater than 0"
            );
        }

        // Unit price validation
        if (orderItem.getUnitPrice() == null ||
                orderItem.getUnitPrice() <= 0) {

            throw new BadRequestException(
                    "Unit price must be greater than 0"
            );
        }

        // Subtotal validation
        if (orderItem.getSubTotal() == null) {
            throw new BadRequestException(
                    "Subtotal is required"
            );
        }

        double expectedSubTotal =
                orderItem.getQuantity() *
                        orderItem.getUnitPrice();

        if (Math.abs(
                orderItem.getSubTotal() - expectedSubTotal
        ) > 0.01) {

            throw new BadRequestException(
                    "Subtotal must equal quantity multiplied by unit price"
            );
        }

        // Update existing entity
        entity.setOrder(order);
        entity.setMedicine(medicine);
        entity.setQuantity(orderItem.getQuantity());
        entity.setUnitPrice(orderItem.getUnitPrice());
        entity.setSubTotal(orderItem.getSubTotal());

        repository.save(entity);
    }
}