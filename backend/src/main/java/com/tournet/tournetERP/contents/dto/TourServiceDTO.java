package com.tournet.tournetERP.contents.dto;

import com.tournet.tournetERP.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
public class TourServiceDTO {

    private long serviceUuid; //서비스UUID

    private long tourUuid; //서비스UUID

    private String serviceNameKor; //서비스명KOR

    private String serviceNameEng; //서비스명ENG

    private String serviceColor; //서비스별구분색

    private String pickupYn; //픽업여부

    private String servicePrice; //시중가

    private String mealYn; //식사제공여부

    private int adults; //성인기본인원

    private int children; //아동기본인원

    private int maxPeople; //최대인원

    private Date beginDt; //시작일

    private Date endDt; //종료일

    private Date bookBeginDt; //예약시작일

    private Date bookEndDt; //예약종료일

    private int showingOrder; //정렬순서

    private String api; //Fareharbor

    private String departureArea; //출발공항

    private String returnArea; //리턴공항

    private Date createdDt; //생성일

    private String modifiedByName;

    private String createdByName;

    private Date modifiedDt; //최종수정일

    public TourServiceDTO() {
    }

}

