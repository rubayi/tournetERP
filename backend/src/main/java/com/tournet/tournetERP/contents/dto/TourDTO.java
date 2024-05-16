package com.tournet.tournetERP.contents.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * TourDTO!!
 *
 * @author : rubayi
 * @fileName : TourRequest
 * @since : 2024-04-05
 */
@Data
public class TourDTO {

    private long tourUuid; //투어UUID
    private long tourCategory; //투어구분

    private String tourCategoryName; //투어구분
    private String tourCategoryNameEn; //투어구분
    private long tourArea; //지역코드
    private String tourAreaName; //지역코드
    private String tourAreaNameEn; //지역코드

    private long tourAreaSub; //지역코드
    private String tourAreaSubName; //지역코드
    private String tourAreaSubNameEn; //지역코드
    private String tourKor; //투어명KOR
    private String tourEng; //투어명ENG
    private long prepaidMethod; //프리페이드지정]
    private String prepaidMethodName; //프리페이드지정
    private String prepaidMethodNameEn; //프리페이드지정
    private long minAge; //제한연령
    private long childAge; //아이나이
    private long youthAge; //쥬니어나이
    private Date createdDt; //생성일
    private long createdBy; //생성자
    private long modifiedBy; //최종수정자
    private Date modifiedDt; //최종수정일

    private String beginDt; //시작일
    private String endDt; //종료일

    private String createdByName; //생성자
    private String modifiedByName; //최종수정자

    private long searchTourUuid;
    private long searchTourCategory;
    private long searchTourArea;
    private long searchTourAreaSub;
    private long searchPrepaidMethod;

    private String searchTourKor;
    private String searchTourEng;

    private long tourOrd; //투어명ENG

    public TourDTO () {
    }
}
