package com.tournet.tournetERP.auth.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.tournet.tournetERP.common.entity.ComCode;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_EMP",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "empEmail")
        })
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

    @Column(name = "EMP_WORK_TYPE", nullable = true)
    private long empWorkType;

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

    @Column(name = "EMP_ADDRESS1", nullable = true)
    private String empAddress1;

    @Column(name = "EMP_ADDRESS2", nullable = true)
    private String empAddress2;

    @Column(name = "EMP_CITY", nullable = true)
    private String empCity;

    @Column(name = "EMP_STATE", nullable = true)
    private String empState;

    @Column(name = "EMP_COUNTRY", nullable = true)
    private long empCountry;

    @Column(name = "EMP_ZIP", nullable = true)
    private String empZip;

    @Column(name = "EMP_DOB", nullable = true)
    private String empDob;

    @Column(name = "EMP_DOB_TYPE", nullable = true)
    private long empDobType;

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

    @ManyToOne
    @JoinColumn(name = "emp_work_type", referencedColumnName = "code_uuid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ComCode empWorkTypeName;

    @ManyToOne
    @JoinColumn(name = "emp_div", referencedColumnName = "code_uuid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ComCode empDivName;

    @ManyToOne
    @JoinColumn(name = "emp_title", referencedColumnName = "code_uuid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ComCode empTitleName;

    @ManyToOne
    @JoinColumn(name = "emp_role", referencedColumnName = "code_uuid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ComCode empRoleName;

    @ManyToOne
    @JoinColumn(name = "emp_country", referencedColumnName = "code_uuid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ComCode empCountryName;

    @ManyToOne
    @JoinColumn(name = "emp_dob_type", referencedColumnName = "code_uuid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ComCode empDobTypeName;

    @ManyToOne
    @JoinColumn(name = "emp_status", referencedColumnName = "code_uuid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ComCode empStatusName;

    public User() {
    }

    public User(String username, String empEmail, String password,
                String empKor, String empEng, long empWorkType,
                String empWorkPhone, long empDiv, long empTitle,
                String empPhone, String empEmailBook, String empAddress1,
                String empAddress2, String empCity, String empState, long empCountry,
                String empZip, String empDob, long empDobType,
                String empMemo, long empStatus
    ) {
        this.username = username;
        this.empEmail = empEmail;
        this.password = password;
        this.empKor = empKor;
        this.empEng = empEng;
        this.empWorkType = empWorkType;
        this.empWorkPhone = empWorkPhone;
        this.empDiv = empDiv;
        this.empTitle = empTitle;
        this.empPhone = empPhone;

        this.empEmailBook = empEmailBook;
        this.empAddress1 = empAddress1;
        this.empAddress2 = empAddress2;
        this.empCity = empCity;
        this.empState = empState;
        this.empCountry = empCountry;
        this.empZip = empZip;

        this.empDob = empDob;
        this.empDobType = empDobType;
        this.empMemo = empMemo;
        this.empStatus = empStatus;
    }

    // getters and setters
}