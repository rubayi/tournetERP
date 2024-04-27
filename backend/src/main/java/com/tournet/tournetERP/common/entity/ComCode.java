package com.tournet.tournetERP.common.entity;

import com.tournet.tournetERP.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "COM_CODE")
public class ComCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODE_UUID", nullable = false)
    private long codeUuid;

    @Column(name = "CODE_KR", nullable = true)
    private String codeKr;

    @Column(name = "CODE_EN", nullable = true)
    private String codeEn;

    @Column(name = "CODE_ABB", nullable = true)
    private String codeAbb;

    @Column(name = "CODE_LVL", nullable = true)
    private int codeLvl;

    @Column(name = "CODE_ORD", nullable = true)
    private int codeOrd;

    @Column(name = "UPR_CODE_UUID", nullable = true)
    private long uprCodeUuid;

    @Column(name = "USE_YN", nullable = true)
    private String useYn;

    @Column(name = "ETC", nullable = true)
    private String etc;


    @ManyToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "EMP_UUID")
    private User createUser;

    @ManyToOne
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "EMP_UUID")
    private User modifyUser;

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt;


    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt;

    public ComCode() {
    }

}
