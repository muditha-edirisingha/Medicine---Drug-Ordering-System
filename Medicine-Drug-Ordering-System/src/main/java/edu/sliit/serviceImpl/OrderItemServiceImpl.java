package edu.sliit.serviceImpl;

import edu.sliit.dto.OrderItem;
import edu.sliit.entity.OrderEntity;
import edu.sliit.entity.OrderItemEntity;
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
    final ModelMapper mapper;
    @Override
    public List<OrderItem> getOrderItems() {
        List<OrderItem> OrderItems = new ArrayList<>();
        repository.findAll().forEach(orderItem ->{
            OrderItems.add(mapper.map(orderItem, OrderItem.class));


        });
        return OrderItems;
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        repository.save(mapper.map(orderItem, OrderItemEntity.class));
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
        repository.save(
                mapper.map(orderItem, OrderItemEntity.class)
        );
    }
}
