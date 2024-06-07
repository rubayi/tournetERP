package com.tournet.tournetERP.contents.dto;

import com.tournet.tournetERP.auth.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class AmenityDTO {
    
    private long amenityUuid; //편의시설UUID

    private long tourUuid; //투어정보UUID

    private String amenityKor; //편의시설명

    private String amenityEng; //편의시설영문명

    private String amenityDescKor;

    private String amenityDescEng;

    private Date createdDt; //생성일

    private User createdUser;

    private User modifiedUser;

    private Date modifiedDt; //최종수정일

    private String createdByName; //생성자
    
    private String modifiedByName; //최종수정자

    private long searchAmenityUuid;
    private long searchTourUuid;
}
