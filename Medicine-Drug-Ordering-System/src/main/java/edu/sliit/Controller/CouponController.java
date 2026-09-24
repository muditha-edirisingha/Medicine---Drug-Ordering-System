package edu.sliit.Controller;

import edu.sliit.dto.Coupon;
import edu.sliit.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    final CouponService service;

    @GetMapping("/get-all")
    public List<Coupon> getCoupons() {

        return service.getCoupons();
    }

    @GetMapping("/search-by-id/{couponId}")
    public Coupon searchByCouponId(
            @PathVariable Integer couponId) {

        return service.searchByCouponId(couponId);
    }

    @GetMapping("/search-by-promotion-id/{promotionId}")
    public List<Coupon> searchByPromotionId(
            @PathVariable Integer promotionId) {

        return service.searchByPromotionId(promotionId);
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon(
            @RequestBody Coupon coupon) {

        service.addCoupon(coupon);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateCoupon(
            @RequestBody Coupon coupon) {

        service.updateCoupon(coupon);
    }

    @DeleteMapping("/delete/{couponId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCoupon(
            @PathVariable Integer couponId) {

        service.deleteByCouponId(couponId);
    }
}
