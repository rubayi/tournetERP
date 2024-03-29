package com.tournet.tournetERP.contents.dto;

import java.util.Date;
import lombok.Data;
/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : CompanyResponse
 * @since : 2024-03-26
 */

@Data
public class CompanyResponse {

    private long compUuid; //업체UUID

    private long compSector; //유형

    private String compSectorName;

    private String compGroup; //그룹

    private String compKor; //업체명KOR

    private String compEng; //업체명ENG

    private String compAbb; //업체단축코드

    private String compColor; //회사구분색

    private String logoFile; //로고

    private String estDate; //설립일

    private long compRate; //등급

    private String compRateName; //등급

    private long hotelRate; //등급

    private String hotelRateName;

    private long optionRate; //등급

    private String optionRateName;

    private long rentcarRate; //등급

    private String  rentcarRateName;

    private long restaurantRate; //등급

    private String  restaurantRateName;

    private long packRegRate; //등급

    private String  packRegRateName;

    private long packRate; //등급

    private String packRateName;

    private long honeymoonRegRate; //등급

    private String honeymoonRegRateName;

    private long honeymoonRate; //등급

    private String honeymoonRateName;


    private String minAge; //제한연령

    private String childAge; //아이나이

    private String youthAge; //쥬니어나이

    private String couponYn; //쿠폰사용가능유무

    private String prepaidHow; //프리페이드지정

    private String beginDt; //시작일

    private String endDt; //종료일

    private Date createdDt; //생성일

    private long createdBy; //생성자

    private String createdByName; //생성자

    private long modifiedBy; //최종수정자

    private String modifiedByName; //최종수정자

    private Date modifiedDt; //최종수정일

    private int page;

    private int size;

    private String searchKeyword;

    private String searchKeyword2;

    private String searchKeyword3;
}
