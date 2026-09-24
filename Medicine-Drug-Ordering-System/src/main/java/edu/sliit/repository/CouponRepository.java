package edu.sliit.repository;

import edu.sliit.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {
    List<CouponEntity> findByPromotion_PromotionId(Integer promotionId);

    void deleteByPromotion_PromotionId(Integer promotionId);


}
