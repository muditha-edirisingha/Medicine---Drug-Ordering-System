package edu.sliit.serviceImpl;

import edu.sliit.dto.Promotion;
import edu.sliit.entity.PromotionEntity;
import edu.sliit.repository.CouponRepository;
import edu.sliit.repository.PromotionRepository;
import edu.sliit.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.sliit.entity.MarketingOfficerEntity;
import edu.sliit.repository.MarketingOfficerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    final PromotionRepository repository;
    final CouponRepository couponRepository;
    final MarketingOfficerRepository marketingOfficerRepository;
    final ModelMapper mapper;

    @Override
    public List<Promotion> getPromotions() {

        List<Promotion> promotions = new ArrayList<>();

        repository.findAll().forEach(promotionEntity -> {

            Promotion promotion =
                    mapper.map(promotionEntity, Promotion.class);

            if (promotionEntity.getMarketingOfficer() != null) {
                promotion.setMarketingOfficerId(
                        promotionEntity.getMarketingOfficer()
                                .getMarketingOfficerId()
                );
            }

            promotions.add(promotion);
        });

        return promotions;
    }

    @Override
    public void addPromotion(Promotion promotion) {

        if (promotion.getStatus() == null) {
            promotion.setStatus("ACTIVE");
        }

        PromotionEntity entity =
                mapper.map(promotion, PromotionEntity.class);

        MarketingOfficerEntity marketingOfficer =
                marketingOfficerRepository
                        .findById(promotion.getMarketingOfficerId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Marketing Officer not found"
                                ));

        entity.setMarketingOfficer(marketingOfficer);

        repository.save(entity);
    }

    @Override
    public Promotion searchByPromotionId(Integer promotionId) {

        PromotionEntity entity =
                repository.findById(promotionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Promotion not found"
                                ));

        Promotion promotion =
                mapper.map(entity, Promotion.class);

        if (entity.getMarketingOfficer() != null) {
            promotion.setMarketingOfficerId(
                    entity.getMarketingOfficer()
                            .getMarketingOfficerId()
            );
        }

        return promotion;
    }

    @Override
    public List<Promotion> searchByPromotionName(
            String promotionName) {

        List<Promotion> promotions = new ArrayList<>();

        repository.findByPromotionNameContainingIgnoreCase(
                promotionName
        ).forEach(promotionEntity -> {

            Promotion promotion =
                    mapper.map(
                            promotionEntity,
                            Promotion.class
                    );

            if (promotionEntity.getMarketingOfficer() != null) {
                promotion.setMarketingOfficerId(
                        promotionEntity.getMarketingOfficer()
                                .getMarketingOfficerId()
                );
            }

            promotions.add(promotion);
        });

        return promotions;
    }

    @Override
    public List<Promotion> searchByStatus(String status) {

        List<Promotion> promotions = new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(promotionEntity -> {

                    Promotion promotion =
                            mapper.map(
                                    promotionEntity,
                                    Promotion.class
                            );

                    if (promotionEntity.getMarketingOfficer() != null) {
                        promotion.setMarketingOfficerId(
                                promotionEntity.getMarketingOfficer()
                                        .getMarketingOfficerId()
                        );
                    }

                    promotions.add(promotion);
                });

        return promotions;
    }

    @Override
    public void updatePromotion(Promotion promotion) {

        if (!repository.existsById(
                promotion.getPromotionId())) {

            throw new RuntimeException(
                    "Promotion not found"
            );
        }

        PromotionEntity entity =
                mapper.map(
                        promotion,
                        PromotionEntity.class
                );

        MarketingOfficerEntity marketingOfficer =
                marketingOfficerRepository
                        .findById(promotion.getMarketingOfficerId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Marketing Officer not found"
                                ));

        entity.setMarketingOfficer(marketingOfficer);

        repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteByPromotionId(Integer promotionId) {
        if (!repository.existsById(promotionId)) {

            throw new RuntimeException(
                    "Promotion not found"
            );
        }

        couponRepository.deleteByPromotion_PromotionId(promotionId);

        repository.deleteById(promotionId);
    }
}
