package com.tournet.tournetERP.common.dto;

import java.util.Date;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @createdDt : ComMenuResponse
 * @since : 2024-03-06
 */

import lombok.Builder;
import lombok.Data;

@Data
public class ComMenuResponse {

    private int menuUuid;

    private long upperMenuUuid;

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

    private long empMenuUuid;

    @Builder
    public ComMenuResponse(
            int menuUuid,
            long upperMenuUuid,
            long roleUuid,
            long menuLvl,
            String menuIcon,
            String menuKor,
            String menuEng,
            String menuUrl,
            int menuOrd,
            String menuDesc,
            String useYn,
            Date createdDt, //생성일
            int createdBy, //생성자
            int modifiedBy, //최종수정자
            Date modifiedDt //최종수정일

    ) {
        this.menuUuid = menuUuid;
        this.upperMenuUuid = upperMenuUuid;
        this.roleUuid = roleUuid;
        this.menuLvl = menuLvl;
        this.menuIcon = menuIcon;
        this.menuKor = menuKor;
        this.menuEng = menuEng;
        this.menuUrl = menuUrl;
        this.menuOrd = menuOrd;
        this.menuDesc = menuDesc;
        this.useYn = useYn;
        this.createdDt = createdDt;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.modifiedDt = modifiedDt;

    }
}
