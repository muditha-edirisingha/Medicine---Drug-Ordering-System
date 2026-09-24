package edu.sliit.repository;

import edu.sliit.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer> {

    List<PromotionEntity> findByPromotionNameContainingIgnoreCase(String promotionName);

    List<PromotionEntity> findByStatusIgnoreCase(String status);
}
