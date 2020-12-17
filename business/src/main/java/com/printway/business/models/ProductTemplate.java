package com.printway.business.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_templates")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductTemplate {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Basic
    @Column(name = "code", length = 20, unique = true, nullable = false)
    private String code;

    @Basic
    @Column(name = "base_cost", nullable = false)
    private Double baseCost;

    @Basic
    @Column(name = "shipping_time")
    private String shippingTime;

    @Basic
    @Column(name = "processing_time")
    private String processingTime;

    @Basic
    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Lob
    @Column(name = "design")
    private String design;
}
