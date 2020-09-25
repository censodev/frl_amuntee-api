package com.amuntee.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders", schema = "amuntee_business", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "shopify_code", nullable = true, length = 50)
    private String shopifyCode;

    @Basic
    @Column(name = "shopify_date", nullable = true)
    private Timestamp shopifyDate;

    @Basic
    @Column(name = "shopify_product_code", nullable = true, length = 20)
    private String shopifyProductCode;

    @Basic
    @Column(name = "shopify_quantity", nullable = true)
    private Integer shopifyQuantity;

    @Basic
    @Column(name = "shopify_seller_id", nullable = true, length = 2)
    private String shopifySellerId;

    @Basic
    @Column(name = "shopify_price", nullable = true, precision = 0)
    private Double shopifyPrice;

    @Basic
    @Column(name = "paygate_id", nullable = true, length = 100)
    private String paygateId;

    @Basic
    @Column(name = "paygate_name", nullable = true, length = 45)
    private String paygateName;

    @Basic
    @Column(name = "shopify_status", nullable = true, length = 45)
    private String shopifyStatus;

    @Basic
    @Column(name = "shopify_design_code", nullable = true, length = 20)
    private String shopifyDesignCode;

    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
}
