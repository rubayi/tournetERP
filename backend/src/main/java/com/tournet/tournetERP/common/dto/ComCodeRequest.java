package com.tournet.tournetERP.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;


@Data
public class ComCodeRequest {

    private int codeUuid;

    @NotNull(message = "코드명을 입력 해 주십시오.")
    @Size(min = 2, max = 50)
    private String codeKr;

    private String codeEn;

    private String codeValue;

    private String codeLvl;

    private int codeOrd;

    private int uprCodeUuid;

    private String useYn;

    private String createdBy;

    private Date createdDt = new Date();

    private String modifiedBy;

    private Date modifiedDt = new Date();

    private String etc;
}
