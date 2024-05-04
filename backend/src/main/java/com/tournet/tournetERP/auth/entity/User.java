package com.tournet.tournetERP.auth.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_EMP")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_UUID", nullable = false)
    private long empUuid;

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

    @Column(name = "EMP_DIV", nullable = true)
    private long empDiv;

    @Column(name = "EMP_TITLE", nullable = true)
    private long empTitle;

    @Column(name = "EMP_ROLE", nullable = true)
    private long empRole;

    @Column(name = "EMP_PHONE", nullable = true)
    private String empPhone;

    @Column(name = "EMP_WORK_PHONE", nullable = true)
    private String empWorkPhone;

    @Column(name = "EMP_EMAIL_BOOK", nullable = true)
    private String empEmailBook;

    @Column(name = "EMP_ADDRESS", nullable = true)
    private String empAddress;

    @Column(name = "EMP_Office", nullable = true)
    private long empOffice;

    @Column(name = "EMP_COMP", nullable = true)
    private long empComp;

    @Column(name = "EMP_DOB", nullable = true)
    private String empDob;

    @Column(name = "EMP_MEMO", nullable = true)
    private String empMemo;

    @Column(name = "BACK_COLOR", nullable = true)
    private String backColor;

    @Column(name = "FONT_COLOR", nullable = true)
    private String fontColor;

    @Column(name = "EMP_BEGIN_DT", nullable = true)
    private String empBeginDt;

    @Column(name = "EMP_END_DT", nullable = true)
    private String empEndDt;

    @Column(name = "EMP_STATUS", nullable = true)
    private long empStatus;

    @Column(name = "MODIFIED_BY", nullable = true)
    private long modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = false)
    private Date modifiedDt;

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
    @JoinTable(name = "emp_role",
            joinColumns = @JoinColumn(name = "emp_uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_uuid"))
    private Set<Role> roles = new HashSet<>();


    public User() {
    }

    public User(String username, String empEmail, String password,
                String empKor, String empEng, String empWorkPhone,
                long empDiv, long empTitle, String empPhone,
                String empEmailBook, String empAddress, long empOffice, 
                long empComp, String empDob, String empMemo, long empStatus,
                long modifiedBy
    ) {
        this.username = username;
        this.empEmail = empEmail;
        this.password = password;
        this.empKor = empKor;
        this.empEng = empEng;
        this.empWorkPhone = empWorkPhone;
        this.empDiv = empDiv;
        this.empTitle = empTitle;
        this.empPhone = empPhone;
        this.empEmailBook = empEmailBook;
        this.empAddress = empAddress;
        this.empOffice = empOffice;
        this.empComp = empComp;
        this.empDob = empDob;
        this.empMemo = empMemo;
        this.empStatus = empStatus;
        this.modifiedBy = modifiedBy;
    }

    // getters and setters
}