package com.tournet.tournetERP.contents.dto;

import com.tournet.tournetERP.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
public class InfoDTO {

    private long infoUuid; //안내UUID

    private long searchInfoUuid;

    private long tourUuid; //투어UUID

    private long searchTourUuid; //투어UUID

    private long infoType; //구분

    private String infoTypeName; //구분

    private String infoTypeNameEn;

    private String contentKor; //내용KOR

    private String contentEng; //내용ENG

    private Date applyBeginDt; //적용시작

    private Date applyEndDt; //적용종료

    private String applyStandard; //취소적용기준

    private Date createdDt; //생성일

    private String modifiedByName;

    private String createdByName;

    private Date modifiedDt; //최종수정일

    public InfoDTO() {
    }

}


