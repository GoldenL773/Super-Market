package org.example.spring.supermarket.service;

import org.example.spring.supermarket.entity.Promotion;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PromotionService {
    List<Promotion> getAllPromotions();
    Optional<Promotion> getPromotionById(int id);
    List<Promotion> getActivePromotions();
    List<Promotion> getPromotionsByDateRange(Date startDate, Date endDate);
    Promotion savePromotion(Promotion promotion);
    void deletePromotion(int id);
    boolean isPromotionValid(int promotionId);
}