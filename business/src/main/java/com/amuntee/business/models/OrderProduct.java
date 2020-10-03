package com.amuntee.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "orders_products", schema = "amuntee_business", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderProduct {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "order_code", length = 50)
    private String orderCode;

    @Basic
    @Column(name = "product_code", length = 50)
    private String productCode;

    @Basic
    @Column(name = "title", length = 100)
    private String title;

    @Basic
    @Column(name = "sku", length = 50)
    private String sku;

    @Basic
    @Column(name = "supplier_code", length = 50)
    private String supplierCode;

    @Basic
    @Column(name = "design_code", length = 50)
    private String designCode;

    @Basic
    @Column(name = "seller_code", length = 50)
    private String sellerCode;

    @Basic
    @Column(name = "quantity", length = 50)
    private int quantity;
}
