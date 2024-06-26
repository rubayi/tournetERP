package com.tournet.tournetERP.common.dto;

import lombok.Data;

@Data
public class UserMenuDTO {
    
    private int empMenuUuid;

    private int roleUuid;
    
    private int menuUuid;
    
    private String menuRead;
    
    private String menuWrite;
    
    private String menuDelete;

    public UserMenuDTO() {
    }

}
