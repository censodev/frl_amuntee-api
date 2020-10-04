package com.printway.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "code", nullable = true, length = 20)
    private String code;

    @Basic
    @Column(name = "name", nullable = true, length = 200)
    private String name;

    @Basic
    @Column(name = "type", nullable = true, length = 45)
    private String type;

    @Basic
    @Column(name = "base_cost", nullable = true, precision = 0)
    private Double baseCost;

    @Basic
    @Column(name = "supplier_id", nullable = true)
    private Integer supplierId;

    @Basic
    @Column(name = "shipping_time", length = 150)
    private String shippingTime;

    @Basic
    @Column(name = "processing_time", length = 150)
    private String processingTime;

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Basic
    @Column(name = "created_by", nullable = true)
    private Integer createdBy;

    @Basic
    @Column(name = "updated_by", nullable = true)
    private Integer updatedBy;

}
