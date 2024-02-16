package com.tournet.tournetERP.security.entity;

import java.time.Instant;

import com.tournet.tournetERP.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "APP_REFRESHTOKEN")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_UUID", nullable = false)
    private int tokenUuid;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name = "EMP_UUID", referencedColumnName = "EMP_UUID")
    private User user;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private Instant expiryDate;
}

