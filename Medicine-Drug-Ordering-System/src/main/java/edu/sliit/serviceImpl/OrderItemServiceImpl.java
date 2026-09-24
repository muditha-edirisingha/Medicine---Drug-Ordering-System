package edu.sliit.serviceImpl;

import edu.sliit.dto.OrderItem;
import edu.sliit.entity.MedicineEntity;
import edu.sliit.entity.OrderEntity;
import edu.sliit.entity.OrderItemEntity;
import edu.sliit.repository.MedicineRepository;
import edu.sliit.repository.OrderItemRepository;
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

        OrderItemEntity entity =
                mapper.map(orderItem, OrderItemEntity.class);

        // Set Order relationship
        OrderEntity order = new OrderEntity();
        order.setOrderId(orderItem.getOrderId());
        entity.setOrder(order);

        // Set Medicine relationship
        MedicineEntity medicine = new MedicineEntity();
        medicine.setMedicineId(orderItem.getMedicineId());
        entity.setMedicine(medicine);

        repository.save(entity);
    }

    @Override
    public OrderItem searchByOrderItemId(Integer orderItemId) {
        OrderItemEntity entity = repository.findById(orderItemId)
                .orElseThrow(() ->
                        new RuntimeException("Order Item not found"));

        return mapper.map(entity, OrderItem.class);
    }

    @Override
    public void deleteByOrderItemId(Integer orderItemId) {
        repository.deleteById(orderItemId);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        OrderItemEntity entity =
                mapper.map(orderItem, OrderItemEntity.class);

        OrderEntity order =
                new OrderEntity();

        order.setOrderId(orderItem.getOrderId());

        entity.setOrder(order);

        repository.save(entity);
    }
}
