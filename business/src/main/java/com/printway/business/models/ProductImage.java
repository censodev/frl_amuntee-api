package com.printway.business.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "shopify_id", nullable = false)
    private long shopifyId;

    @Column(name = "position")
    private int position;

    @Column(name = "src")
    private String src;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Lob
    @Column(name = "attachment")
    private String attachment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
