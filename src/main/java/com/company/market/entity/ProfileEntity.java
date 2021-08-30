package com.company.market.entity;

import com.company.market.enums.ProfileStatus;
import com.company.market.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = ("profile"))
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private String password;
    @Column
    private String contact;
    @Column
    private LocalDateTime createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ("attach_id"))
    private ImageEntity imageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ("address_id"))
    private AddressEntity address;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
}
