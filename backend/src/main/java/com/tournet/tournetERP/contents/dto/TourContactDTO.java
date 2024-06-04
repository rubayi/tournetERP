package com.tournet.tournetERP.contents.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
public class TourContactDTO {

    private long contactUuid; //연락처UUID
    private long tourUuid; //연락처UUID
    private long contactType; //유형
    private String contactTypeName;
    private String contactTypeNameEn;
    private long repYn; //대표연락처여부
    private String repYnName;
    private String repYnNameEn;
    private String contactCont; //연락처

    public TourContactDTO() {
    }

}

