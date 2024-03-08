package com.tournet.tournetERP.contents.entity;

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
    private int OptionUuid; //추가비용UUID

    @Column(name = "OPTION_KOR", nullable = true)
    private String optionKor; //추가비용명KOR

    @Column(name = "OPTION_ENG", nullable = true)
    private String optionEng; //추가비용명ENG

    @Column(name = "OPTION_PRICE", nullable = true)
    private String optionPrice; //원가

    @Column(name = "OPTION_NET", nullable = true)
    private String optionNet; //넷가

    @Column(name = "OPTION_TAX", nullable = true)
    private String optionTax; //세금

    @Column(name = "TAX_HOW", nullable = true)
    private String taxHow; //세금적용방법

    @Column(name = "BEGIN_DT", nullable = true)
    private Date beginDt; //시작일

    @Column(name = "END_DT", nullable = true)
    private Date endDt; //종료일

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @Column(name = "CREATED_BY", nullable = true)
    private int createdBy; //생성자

    @Column(name = "MODIFIED_BY", nullable = true)
    private int modifiedBy; //최종수정자

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일

    public Option () {
    }

    public Option (          int OptionUuid,  String optionKor,  String optionEng,
                             String optionPrice,  String optionNet,  String optionTax,
                             String taxHow,  Date beginDt,  Date endDt,
                             Date createdDt,  int createdBy,  int modifiedBy,
                             Date modifiedDt   ) {
        this.OptionUuid=OptionUuid;
        this.optionKor=optionKor;
        this.optionEng=optionEng;
        this.optionPrice=optionPrice;
        this.optionNet=optionNet;
        this.optionTax=optionTax;
        this.taxHow=taxHow;
        this.beginDt=beginDt;
        this.endDt=endDt;
        this.createdDt=createdDt;
        this.createdBy=createdBy;
        this.modifiedBy=modifiedBy;
        this.modifiedDt=modifiedDt;

    }
}


