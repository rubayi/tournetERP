package com.tournet.tournetERP.auth.dto;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {

    private int empUuid;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String empEmail;

    private String empKor;

    private String empEng;

    private String empImg;

    private String empWorkType;

    private String empDiv;

    private String empTitle;

    private String empRole;

    private String empPhone;

    private String empWorkPhone;

    private String empEmailBook;

    private String empAddress1;

    private String empAddress2;

    private String empCity;

    private String empState;

    private String empCountry;

    private String empZip;

    private String empDob;

    private String empDobType;

    private String empMemo;

 private String empStatus;

    private String empBeginDt;

    private String empEndDt;

    private Set<String> role;

    private String backColor;

    private String fontColor;

    private long menuAuthUuid;

    private String menuRead; //읽기

    private String menuUpdate; //수정

    private String menuDelete; //삭제

    private int page;

    private int size;

    private String password;

}
