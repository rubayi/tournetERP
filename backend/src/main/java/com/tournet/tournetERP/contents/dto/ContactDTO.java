package com.tournet.tournetERP.contents.dto;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ContactDTO
 * @since : 2024-04-09
 */
import lombok.Data;
import java.util.Date;

@Data
public class ContactDTO {

    private long contactUuid;
    private long compUuid;
    private long contactType;
    private String contactTypeName;
    private long repYn;
    private String repYnName;
    private String contactCont;
}
