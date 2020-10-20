package com.printway.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "marketing_fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MarketingFee {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    @Column(name = "campaign_id")
    private String campaignId;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "seller_code")
    private String sellerCode;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "stop_time")
    private LocalDateTime stopTime;

    @Column(name = "spend")
    private Double spend;

    @Column(name = "spend_per_day")
    private Double spendPerDay;
}
