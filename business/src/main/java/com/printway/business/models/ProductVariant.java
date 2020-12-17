package com.printway.business.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_variants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "shopify_id", nullable = false)
    private long shopifyId;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "compare_at_price")
    private double compareAtPrice;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "image_id")
    private long imageId;

    @Column(name = "price")
    private double price;

    @Column(name = "sku")
    private String sku;

    @Column(name = "title")
    private String title;

    @Column(name= "updated_at")
    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "option1")
    private String option1;

    @Column(name = "option2")
    private String option2;

    @Column(name = "option3")
    private String option3;
}
