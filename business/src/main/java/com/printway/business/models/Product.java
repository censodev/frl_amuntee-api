package com.printway.business.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProductImage> images;

    @Basic
    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "body_html")
    private String bodyHtml;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "published_scope")
    private String publishedScope;

    @Column(name = "tags")
    private String tags;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProductVariant> variants;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "shopify_id", nullable = false)
    private long shopifyId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_template_id")
    private ProductTemplate productTemplate;

    @Basic
    @Column(name = "created_by", nullable = true)
    private Integer createdBy;
}
