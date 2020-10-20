package com.printway.business.repositories;

import com.printway.business.models.MarketingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MarketingFeeRepository extends JpaRepository<MarketingFee, Integer> {
    List<MarketingFee> findByCampaignId(String id);
    List<MarketingFee> findAllBySellerCodeAndStartTimeLessThanEqualAndStopTimeGreaterThanEqual(String sellerCode, LocalDateTime startTime, LocalDateTime stopTime);
    List<MarketingFee> findAllByStartTimeLessThanEqualAndStopTimeGreaterThanEqual(LocalDateTime startTime, LocalDateTime stopTime);
}
