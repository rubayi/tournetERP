package com.tournet.tournetERP.contents.dto;

import com.tournet.tournetERP.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : HotelRequest
 * @since : 2024-04-01
 */
@Data
public class HotelDTO {

    private long hotelUuid; //호텔정보UUID

    private long tourUuid; //투어정보UUID

    private long hotelLvl; //호텔등급

    private String hotelLvlName;

    private long hotelGrp; //호텔그룹

    private String  hotelGrpName;

    private long childAgeBreakfast; //조식어린이나이

    private String checkinTime; //호텔체크인

    private String checkoutTime; //호텔체크아웃

    private String resortFee; //리조트비용

    private Date createdDt; //생성일

    private User createdUser;

    private User modifiedUser;

    private String createdByName; //생성자
    private String modifiedByName; //최종수정자

    private Date modifiedDt; //최종수정일
}
