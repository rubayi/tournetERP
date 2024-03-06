package com.tournet.tournetERP.contents.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
public class CompanyRequest {

    private int CompUuid; //업체UUID

    private String compSector; //유형

    private String compGroup; //그룹

    private String compKor; //업체명KOR

    private String compEng; //업체명ENG

    private String compAbbreviation; //업체단축코드

    private String compColor; //회사구분색

    private String logoFile; //로고

    private Date establishmentDate; //설립일

    private String companyRate; //등급

    private String minAge; //제한연령

    private String childAge; //아이나이

    private String youthAge; //쥬니어나이

    private String couponUseYn; //쿠폰사용가능유무

    private String prepaidHow; //프리페이드지정

    private Date beginDt; //시작일

    private Date endDt; //종료일

    private Date createdDt; //생성일

    private int createdBy; //생성자

    private int modifiedBy; //최종수정자

    private Date modifiedDt; //최종수정일

    private int page;

    private int size;

    private String searchKeyword;

    private String searchKeyword2;

    private String searchKeyword3;

    public CompanyRequest() {
    }

    public CompanyRequest(int CompUuid, String compSector, String compGroup,
                          String compKor, String compEng, String compAbbreviation,
                          String compColor, String logoFile, Date establishmentDate,
                          String companyRate, String minAge, String childAge,
                          String youthAge, String couponUseYn, String prepaidHow,
                          Date beginDt, Date endDt, Date createdDt,
                          int createdBy, int modifiedBy, Date modifiedDt
    ) {
        this.CompUuid=CompUuid;
        this.compSector=compSector;
        this.compGroup=compGroup;
        this.compKor=compKor;
        this.compEng=compEng;
        this.compAbbreviation=compAbbreviation;
        this.compColor=compColor;
        this.logoFile=logoFile;
        this.establishmentDate=establishmentDate;
        this.companyRate=companyRate;
        this.minAge=minAge;
        this.childAge=childAge;
        this.youthAge=youthAge;
        this.couponUseYn=couponUseYn;
        this.prepaidHow=prepaidHow;
        this.beginDt=beginDt;
        this.endDt=endDt;
        this.createdDt=createdDt;
        this.createdBy=createdBy;
        this.modifiedBy=modifiedBy;
        this.modifiedDt=modifiedDt;

    }
}
