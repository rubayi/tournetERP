package com.tournet.tournetERP.auth.entity;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Data
@Table(name = "APP_EMP",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "empEmail")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_UUID", nullable = false)
    private Long empUuid;

    @NotBlank
    @Size(max = 20)
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotBlank
    @Size(max = 20)
    @Column(name = "EMP_KOR", nullable = true)
    private String empKor;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "EMP_EMAIL", nullable = false)
    private String empEmail;

    @NotBlank
    @Size(max = 120)
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "emp_role",
            joinColumns = @JoinColumn(name = "emp_uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_uuid"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String empEmail, String password) {
        this.username = username;
        this.empEmail = empEmail;
        this.password = password;
    }

    // getters and setters
}