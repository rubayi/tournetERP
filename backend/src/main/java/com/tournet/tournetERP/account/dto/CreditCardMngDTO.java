package com.tournet.tournetERP.account.dto;

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
 * @fileName : CreditCardMngDTO
 * @since : 2024-04-08
 */
@Data
public class CreditCardMngDTO {
    private long creditCardUuid; //카드UUID

    private String mngNameKor; //관리명KOR

    private String mngNameEng; //관리명ENG

    private String cardNumber; //카드번호

    private String nameOnCard; //명의자

    private String expMonth; //유효일달

    private String expYear; //유효일년

    private Date createdDt; //생성일

    private String beginDt; //시작일

    private String endDt; //종료일

    private User createUser;

    private User modifyUser;

    private Date modifiedDt; //최종수정일

    private String searchKeyword;

    private String searchKeyword2;

    private String searchKeyword3;

    private String createdByName; //생성자

    private String modifiedByName; //최종수정자

}
