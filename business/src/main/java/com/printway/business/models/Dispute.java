package com.printway.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "disputes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dispute {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    @Column(name = "order_id")
    private String orderId;

    private String transaction;
    private Double amount;

    @Column(name = "dispute_fee")
    private Double disputeFee;

    @Column(name = "paygate_name")
    private String paygateName;

    private Double total;

    @Column(name = "seller_id")
    private String sellerId;

    private String reason;
    private LocalDateTime time;
}
