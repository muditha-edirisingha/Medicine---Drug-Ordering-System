package edu.sliit.repository;

import edu.sliit.entity.OrderEntity;
import edu.sliit.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
}
