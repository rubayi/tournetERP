package com.tournet.tournetERP.auth.dto;

import java.util.Set;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {
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

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
