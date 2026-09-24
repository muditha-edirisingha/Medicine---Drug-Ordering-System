package edu.sliit.service;

import edu.sliit.dto.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getCoupons();

    void addCoupon(Coupon coupon);

    Coupon searchByCouponId(Integer couponId);

    List<Coupon> searchByPromotionId(
            Integer promotionId);

    void updateCoupon(Coupon coupon);

    void deleteByCouponId(Integer couponId);
}
