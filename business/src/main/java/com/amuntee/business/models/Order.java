package com.amuntee.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "code", nullable = true, length = 50)
    private String code;

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Basic
    @Column(name = "sub_total_price", nullable = true, precision = 0)
    private Double subTotalPrice;

    @Basic
    @Column(name = "total_price", nullable = true, precision = 0)
    private Double totalPrice;

    @Basic
    @Column(name = "paygate_name", nullable = true, length = 45)
    private String paygateName;

    @Basic
    @Column(name = "financial_status", nullable = true, length = 45)
    private String financialStatus;

    @Basic
    @Column(name = "fulfillment_status", nullable = true, length = 45)
    private String fulfillmentStatus;

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Basic
    @Column(name = "closed_at", nullable = true)
    private LocalDateTime closedAt;
}
