package com.company.market.entity;

import com.company.market.enums.OrderStatus;
import com.company.market.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
//@Table(name = ("order"))
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = ("profile_id"))
    private Integer profileId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ("profile_id"), insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = ("delivery_date"))
    private LocalDateTime deliveryDate;

    @Column
    private String requirement;
    @Column
    private String contact;
    @Column(name = ("payment_type"))
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = ("order_status"))
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = ("address_id"))
    private Integer addressId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ("address_id"), insertable = false, updatable = false)
    private AddressEntity address;

    @Column(name = ("created_date"))
    private LocalDateTime createdDate;
    @Column(name = ("delivered_date"))
    private LocalDateTime deliveredDate;

}
