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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    final PromotionRepository repository;
    final CouponRepository couponRepository;
    final ModelMapper mapper;
    @Override
    public List<Promotion> getPromotions() {
        List<Promotion> promotions = new ArrayList<>();

        repository.findAll().forEach(promotion -> {

            promotions.add(
                    mapper.map(promotion, Promotion.class)
            );

        });

        return promotions;
    }

    @Override
    public void addPromotion(Promotion promotion) {
        if (promotion.getStatus() == null) {
            promotion.setStatus("ACTIVE");
        }

        repository.save(
                mapper.map(
                        promotion,
                        PromotionEntity.class
                )
        );
    }

    @Override
    public Promotion searchByPromotionId(Integer promotionId) {
        PromotionEntity entity =
                repository.findById(promotionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Promotion not found"
                                ));

        return mapper.map(entity, Promotion.class);
    }

    @Override
    public List<Promotion> searchByPromotionName(String promotionName) {
        List<Promotion> promotions = new ArrayList<>();

        repository.findByPromotionNameContainingIgnoreCase(
                promotionName
        ).forEach(promotion -> {

            promotions.add(
                    mapper.map(
                            promotion,
                            Promotion.class
                    )
            );

        });

        return promotions;
    }

    @Override
    public List<Promotion> searchByStatus(String status) {
        List<Promotion> promotions = new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(promotion -> {

                    promotions.add(
                            mapper.map(
                                    promotion,
                                    Promotion.class
                            )
                    );

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

        repository.save(
                mapper.map(
                        promotion,
                        PromotionEntity.class
                )
        );
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
