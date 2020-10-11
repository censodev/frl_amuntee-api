package com.printway.business.models;

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
    private long id;

    @Basic
    @Column(name = "code", nullable = true, length = 50)
    private String code;

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Basic
    @Column(name = "revenue", nullable = true, precision = 0)
    private Double revenue;

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

    @Basic
    @Column(name = "store_id", nullable = false, length = 100)
    private Integer storeId;
}
