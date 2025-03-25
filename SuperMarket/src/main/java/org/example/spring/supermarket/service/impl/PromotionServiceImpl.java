package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.entity.Promotion;
import org.example.spring.supermarket.repository.PromotionRepository;
import org.example.spring.supermarket.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> getPromotionById(int id) {
        return promotionRepository.findById(id);
    }

    @Override
    public List<Promotion> getActivePromotions() {
        return promotionRepository.findActivePromotions();
    }

    @Override
    public List<Promotion> getPromotionsByDateRange(Date startDate, Date endDate) {
        return promotionRepository.findByValidFromBetweenOrValidToBetween(
                startDate, endDate, startDate, endDate);
    }

    @Override
    public Promotion savePromotion(Promotion promotion) {
        if (promotion.getCreatedAt() == null) {
            promotion.setCreatedAt(new Date());
        }
        return promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotion(int id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public boolean isPromotionValid(int promotionId) {
        Optional<Promotion> promotionOpt = promotionRepository.findById(promotionId);

        if (promotionOpt.isPresent()) {
            Promotion promotion = promotionOpt.get();
            Date currentDate = new Date();
            return (promotion.getValidFrom() == null || !currentDate.before(promotion.getValidFrom())) &&
                    (promotion.getValidTo() == null || !currentDate.after(promotion.getValidTo()));
        }

        return false;
    }
}