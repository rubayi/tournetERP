package com.tournet.tournetERP.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Data;

@Entity
@Data
@Table(name = "APP_ROLE")
public class Role {
    @Id
    @Column(name = "ROLE_UUID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleUuid;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole roles;

    public Role() {

    }

    public Role(ERole roles) {
        this.roles = roles;
    }

    // getters and setters
}
