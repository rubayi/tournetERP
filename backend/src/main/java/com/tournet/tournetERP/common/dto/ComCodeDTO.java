package com.tournet.tournetERP.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;


@Data
public class ComCodeDTO {

    private long codeUuid;

    @NotNull(message = "코드명을 입력 해 주십시오.")
    @Size(min = 2, max = 50)
    private String codeKr;

    private String codeEn;

    private int codeLvl;

    private int codeOrd;

    private long uprCodeUuid;

    private String uprNameKr;

    private String uprNameEn;

    private String useYn;

    private String createdBy;

    private String createdByName;

    private Date createdDt = new Date();

    private String modifiedBy;

    private String modifiedByName;

    private Date modifiedDt = new Date();

    private String etc;

    private String searchCodeKr;

    private String searchCodeEn;

    private long searchUprCodeUuid;

    private int searchCodeLvl;
}
