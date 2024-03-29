package com.tournet.tournetERP.auth.dto;

import com.tournet.tournetERP.auth.entity.Role;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.common.entity.ComCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashSet;
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

    private long empWorkType;

    private long empDiv;

    private long empTitle;

    private long empRole;

    private String empPhone;

    private String empWorkPhone;

    private String empEmailBook;

    private String empAddress1;

    private String empAddress2;

    private String empCity;

    private String empState;

    private long empCountry;

    private String empZip;

    private String empDob;

    private long empDobType;

    private String empMemo;

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

    private String empDivName = "";

    private String empTitleName = "";

    private String empRoleName = "";

    private String empCountryName = "";
    private String empDobTypeName = "";
    private String empStatusName = "";

}
