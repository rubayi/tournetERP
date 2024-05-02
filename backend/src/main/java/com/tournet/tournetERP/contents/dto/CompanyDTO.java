package com.tournet.tournetERP.contents.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CompanyDTO {

    private long compUuid; //업체UUID

    private long compSector; //유형

    private String compGroup; //그룹

    private String compKor; //업체명KOR

    private String compEng; //업체명ENG

    private String compAbb; //업체단축코드

    private String compColor; //회사구분색

    private String logoFile; //로고

    private Date estDate; //설립일

    private long compRate; //등급

    private long hotelRate; //등급

    private long optionRate; //등급

    private long rentcarRate; //등급

    private long restaurantRate; //등급

    private long packRegRate; //등급

    private long packRate; //등급

    private long honeymoonRegRate; //등급

    private long honeymoonRate; //등급

    private String minAge; //제한연령

    private String childAge; //아이나이

    private String youthAge; //쥬니어나이

    private String couponYn; //쿠폰사용가능유무

    private long prepaidHow; //프리페이드지정

    private String compMemo; //메모

    private Date beginDt; //시작일

    private Date endDt; //종료일

    private Date createdDt; //생성일

    private long createdBy; //생성자

    private long modifiedBy; //최종수정자

    private Date modifiedDt; //최종수정일

    private String compSectorName;

    private String compRateName; //등급

    private String hotelRateName;

    private String optionRateName;

    private String  rentcarRateName;

    private String  restaurantRateName;

    private String  packRegRateName;

    private String packRateName;

    private String honeymoonRegRateName;

    private String honeymoonRateName;

    private String searchCompKor;

    private String searchCompEng;

    private long searchCompSector;

    private Date searchBeginDt;

    private Date searchEndDt;

    private String searchCompMemo;

    private long searchCompUuid;

    private long searchCompRate;

    private long searchCouponYn;

    private long searchCompGroup;

    private String createdByName; //생성자

    private String modifiedByName; //최종수정자


    public CompanyDTO() {
    }


}
