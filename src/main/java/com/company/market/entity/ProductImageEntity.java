package com.company.market.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = ("product_image"))
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = ("product_id"))
    private Integer productId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ("product_id"), insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = ("image_id"))
    private Integer imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ("image_id"), insertable = false, updatable = false)
    private ImageEntity image;
}
