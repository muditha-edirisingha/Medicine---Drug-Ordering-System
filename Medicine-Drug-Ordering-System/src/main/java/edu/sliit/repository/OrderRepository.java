package edu.sliit.repository;

import edu.sliit.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    List<OrderEntity> findByCustomer_CustomerId(Integer customerId);

}
