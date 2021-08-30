package com.company.market.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = ("image"))
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = ("path"))
    private String path;
    @Column(name = ("type"))
    private String type;
    @Column(name = ("size"))
    private Long size;
    @Column(name = ("token"))
    private String token;
    @Column(name = ("created_date"), nullable = false)
    private LocalDateTime createdDate;
}
