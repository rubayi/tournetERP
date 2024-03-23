package com.tournet.tournetERP.auth.dto;

import lombok.Data;

@Data
public class EmpMenuAuthRequest {

    private long empUuid;

    private long[] menuAuthUuids;

    private String authYn="Y";

    private String deleteFlag;

}
