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
@Table(name = "APP_INFO")
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INFO_UUID", nullable = false)
    private int InfoUuid; //안내UUID

    @Column(name = "INFO_TYPE", nullable = true)
    private String infoType; //구분

    @Column(name = "CONTENT_KOR", nullable = true)
    private String contentKor; //내용KOR

    @Column(name = "CONTENT_ENG", nullable = true)
    private String contentEng; //내용ENG

    @Column(name = "APPLY_BEGIN_DT", nullable = true)
    private Date applyBeginDt; //적용시작

    @Column(name = "APPLY_END_DT", nullable = true)
    private Date applyEndDt; //적용종료

    @Column(name = "APPLY_STANDARD", nullable = true)
    private String applyStandard; //취소적용기준

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

    public Info () {
    }

    public Info (          int InfoUuid,  String infoType,  String contentKor,
                           String contentEng,  Date applyBeginDt,  Date applyEndDt,
                           String applyStandard,  Date createdDt,  int createdBy,
                           int modifiedBy,  Date modifiedDt
    ) {
        this.InfoUuid=InfoUuid;
        this.infoType=infoType;
        this.contentKor=contentKor;
        this.contentEng=contentEng;
        this.applyBeginDt=applyBeginDt;
        this.applyEndDt=applyEndDt;
        this.applyStandard=applyStandard;
        this.createdDt=createdDt;
        this.createdBy=createdBy;
        this.modifiedBy=modifiedBy;
        this.modifiedDt=modifiedDt;

    }
}


