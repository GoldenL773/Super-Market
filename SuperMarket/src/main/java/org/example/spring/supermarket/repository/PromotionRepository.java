// File: `src/main/java/org/example/spring/supermarket/repository/PromotionRepository.java`
package org.example.spring.supermarket.repository;

import org.example.spring.supermarket.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    @Query("SELECT p FROM Promotion p WHERE (p.validFrom IS NULL OR CURRENT_DATE >= p.validFrom) " +
            "AND (p.validTo IS NULL OR CURRENT_DATE <= p.validTo)")
    List<Promotion> findActivePromotions();


    List<Promotion> findByValidFromBetweenOrValidToBetween(Date validFromStart, Date validFromEnd,
                                                           Date validToStart, Date validToEnd);

}