package edu.sliit.service;

import edu.sliit.dto.Promotion;

import java.util.List;

public interface PromotionService {

    List<Promotion> getPromotions();

    void addPromotion(Promotion promotion);

    Promotion searchByPromotionId(Integer promotionId);

    List<Promotion> searchByPromotionName(String promotionName);

    List<Promotion> searchByStatus(String status);

    void updatePromotion(Promotion promotion);

    void deleteByPromotionId(Integer promotionId);
}
