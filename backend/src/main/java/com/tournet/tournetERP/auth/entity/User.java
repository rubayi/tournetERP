package com.tournet.tournetERP.auth.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Size(max = 20)
    @Column(name = "EMP_ENG", nullable = true)
    private String empEng;

    @Size(max = 100)
    @Column(name = "EMP_IMG", nullable = true)
    private String empImg;

    @Column(name = "EMP_WORK_TYPE", nullable = true)
    private String empWorkType;

    @Column(name = "EMP_DIV", nullable = true)
    private String empDiv;

    @Column(name = "EMP_TITLE", nullable = true)
    private String empTitle;

    @Column(name = "EMP_PHONE", nullable = true)
    private String empPhone;

    @Column(name = "EMP_WORK_PHONE", nullable = true)
    private String empWorkPhone;

    @Column(name = "EMP_EMAIL_BOOK", nullable = true)
    private String empEmailBook;

    @Column(name = "EMP_ADDRESS1", nullable = true)
    private String empAddress1;

    @Column(name = "EMP_ADDRESS2", nullable = true)
    private String empAddress2;

    @Column(name = "EMP_CITY", nullable = true)
    private String empCity;

    @Column(name = "EMP_STATE", nullable = true)
    private String empState;

    @Column(name = "EMP_ZIP", nullable = true)
    private String empZip;

    @Column(name = "EMP_DOB", nullable = true)
    private String empDob;

    @Column(name = "EMP_DOB_TYPE", nullable = true)
    private String empDobType;

    @Column(name = "EMP_MEMO", nullable = true)
    private String empMemo;

    @Column(name = "EMP_STATUS", nullable = true)
    private String empStatus;

    @CreatedDate
    @Column(name = "EMP_BEGIN_DT", nullable = true, updatable = false)
    private String empBeginDt;

    @Column(name = "EMP_END_DT", nullable = true, updatable = false)
    private String empEndDt;

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

    public User(String username, String empEmail, String password,
                String empKor, String empEng, String empWorkType,
                String empWorkPhone, String empDiv, String empTitle,
                String empPhone, String empEmailBook, String empAddress1,
                String empAddress2, String empCity, String empState,
                String empZip, String empDob, String empDobType,
                String empMemo, String empStatus
    ) {
        this.username = username;
        this.empEmail = empEmail;
        this.password = password;
        this.empKor = empKor;
        this.empEng = empEng;
        this.empWorkType = empWorkType;
        this.empWorkType = empWorkType;
        this.empDiv = empDiv;
        this.empTitle = empTitle;
        this.empPhone = empPhone;
        this.empWorkPhone = empWorkPhone;
        this.empEmailBook = empEmailBook;
        this.empAddress1 = empAddress1;
        this.empAddress2 = empAddress2;
        this.empCity = empCity;
        this.empState = empState;
        this.empZip = empZip;
        this.empDob = empDob;
        this.empDobType = empDobType;
        this.empMemo = empMemo;
        this.empStatus = empStatus;
    }

    // getters and setters
}