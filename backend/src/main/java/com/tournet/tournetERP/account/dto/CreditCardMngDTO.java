package com.tournet.tournetERP.account.dto;

import com.tournet.tournetERP.auth.entity.User;
import lombok.Data;

import java.util.Date;

/**
 * 크레딧 카드 관리
 *
 * @author : rubayi
 * @fileName : CreditCardMngDTO
 * @since : 2024-04-08
 */
@Data
public class CreditCardMngDTO {
    private long cdCdUuid; //카드UUID

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

    private long searchCdCdUuid;

    private String searchMngNameKor;

    private String searchMngNameEng;

    private String searchCardNumber;

    private String createdByName; //생성자

    private String modifiedByName; //최종수정자

}
