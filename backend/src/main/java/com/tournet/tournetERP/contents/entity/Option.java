package com.tournet.tournetERP.contents.entity;

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
@Table(name = "APP_OPTION")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_UUID", nullable = false)
    private long optUuid; //추가비용UUID

    @Column(name = "OPTION_KOR", nullable = true)
    private String optKor; //추가비용명KOR

    @Column(name = "OPTION_ENG", nullable = true)
    private String optEng; //추가비용명ENG

    @Column(name = "OPTION_PRICE", nullable = true)
    private String optPrice; //원가

    @Column(name = "OPTION_NET", nullable = true)
    private String optNet; //넷가

    @Column(name = "OPTION_TAX", nullable = true)
    private String optTax; //세금

    @Column(name = "TAX_HOW", nullable = true)
    private String taxHow; //세금적용방법

    @Column(name = "BEGIN_DT", nullable = true)
    private Date beginDt; //시작일

    @Column(name = "END_DT", nullable = true)
    private Date endDt; //종료일

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @OneToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User createdUser;

    @OneToOne
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User modifiedUser;

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일

    public Option () {
    }

}


