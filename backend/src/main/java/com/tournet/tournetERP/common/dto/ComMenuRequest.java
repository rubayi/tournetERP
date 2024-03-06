package com.tournet.tournetERP.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ComMenuRequest {

    private int menuUuid;

    private long uprMenuUuid;

    private long roleUuid;

    private String role;

    private long menuLvl;

    private String menuIcon;

    private String menuKor;

    private String menuEng;

    private String menuUrl;

    private int menuOrd;

    private String menuDesc;

    private String useYn;

    private Date createdDt; //생성일

    private int createdBy; //생성자

    private int modifiedBy; //최종수정자

    private Date modifiedDt; //최종수정일

    public ComMenuRequest() {
    }
}
