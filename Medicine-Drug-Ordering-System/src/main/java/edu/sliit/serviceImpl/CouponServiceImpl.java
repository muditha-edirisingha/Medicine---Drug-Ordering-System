package edu.sliit.serviceImpl;

import edu.sliit.dto.Coupon;
import edu.sliit.entity.CouponEntity;
import edu.sliit.entity.PromotionEntity;
import edu.sliit.repository.CouponRepository;
import edu.sliit.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    final CouponRepository repository;
    final ModelMapper mapper;
    @Override
    public List<Coupon> getCoupons() {
        List<Coupon> coupons = new ArrayList<>();

        repository.findAll().forEach(couponEntity -> {

            Coupon coupon =
                    mapper.map(couponEntity, Coupon.class);

            if (couponEntity.getPromotion() != null) {
                coupon.setPromotionId(
                        couponEntity.getPromotion().getPromotionId()
                );
            }

            coupons.add(coupon);
        });

        return coupons;
    }

    @Override
    public void addCoupon(Coupon coupon) {
        if (coupon.getUsedCount() == null) {
            coupon.setUsedCount(0);
        }

        if (coupon.getStatus() == null) {
            coupon.setStatus("ACTIVE");
        }

        CouponEntity entity =
                mapper.map(coupon, CouponEntity.class);

        // Set Promotion relationship
        PromotionEntity promotion = new PromotionEntity();
        promotion.setPromotionId(coupon.getPromotionId());

        entity.setPromotion(promotion);

        // Explicitly set status
        entity.setStatus(coupon.getStatus());

        // Explicitly set used count
        entity.setUsedCount(coupon.getUsedCount());

        repository.save(entity);
    }

    @Override
    public Coupon searchByCouponId(Integer couponId) {
        CouponEntity entity =
                repository.findById(couponId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Coupon not found"
                                ));

        return mapper.map(entity, Coupon.class);
    }

    @Override
    public List<Coupon> searchByPromotionId(Integer promotionId) {
        List<Coupon> coupons = new ArrayList<>();

        repository.findByPromotion_PromotionId(promotionId)
                .forEach(coupon -> {

                    coupons.add(mapper.map(coupon, Coupon.class));

                });

        return coupons;
    }

    @Override
    public void updateCoupon(Coupon coupon) {
        if (!repository.existsById(
                coupon.getCouponId())) {

            throw new RuntimeException(
                    "Coupon not found"
            );
        }

        repository.save(
                mapper.map(
                        coupon,
                        CouponEntity.class
                )
        );
    }

    @Override
    public void deleteByCouponId(Integer couponId) {
        if (!repository.existsById(couponId)) {

            throw new RuntimeException(
                    "Coupon not found"
            );
        }

        repository.deleteById(couponId);
    }
}
