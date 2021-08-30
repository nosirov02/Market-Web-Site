package com.company.market.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = ("order_item"))
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ("id"))
    private Integer id;

    @Column(name = ("order_id"))
    private Integer orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ("order_id"), insertable = false, updatable = false)
    private OrderEntity order;

    @Column(name = ("product_id"))
    private Integer productId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ("product_id"), insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = ("amount"))
    private Integer amount;
    @Column(name = ("created_date"))
    private LocalDateTime createdDate;
    @Column
    private double price;
}
