package com.tournet.tournetERP.auth.dto;

import com.tournet.tournetERP.auth.entity.EmpMenuAuth;
import com.tournet.tournetERP.auth.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : UserResponse
 * @since : 2024-03-14
 */
@Data
public class UserResponse {

    private Long empUuid;

    private String username;

    private String empKor;

    private String empEng;

    private String empImg;

    private long empDiv;

    private long empTitle;

    private long empRole;

    private String empPhone;

    private String empWorkPhone;

    private String empEmailBook;

    private String empAddress;

    private long empOffice;

    private String empDob;

    private String empMemo;

    private long empComp;

    private String backColor;

    private String fontColor;

    private String empBeginDt;

    private String empEndDt;

    private long empStatus;

    private Date modifiedDt;

    private String empEmail;

    private String password;

    private Set<Role> roles = new HashSet<>();

    private String empWorkTypeName = "";

    private String empWorkTypeNameEn = "";

    private String empDivName = "";

    private String empDivNameEn = "";

    private String empTitleName = "";

    private String empTitleNameEn = "";

    private String empRoleName = "";

    private String empRoleNameEn = "";

    private List<EmpMenuAuth> menuAuths;

    private String empCountryName = "";

    private String empCountryNameEn = "";

    private String empDobTypeName = "";

    private String empCompName = "";

    private String empCompNameEn = "";

    private String empStatusName = "";
    
    private String empStatusNameEn = "";

    private String car15seats = "";

    private String car25seats = "";

    private String carPersonal = "";

    private String empFax = "";

    private String empExtenNum = "";

}
