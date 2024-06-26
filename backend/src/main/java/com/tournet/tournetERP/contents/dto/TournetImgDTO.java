package com.tournet.tournetERP.contents.dto;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetImgDTO
 * @since : 2024-06-25
 */
import lombok.Data;
@Data
public class TournetImgDTO {

    private long trnImgUuid;
    private long trnCarUuid; //차량UUID
    private String imgFile;
    private String imgOrd;

    private long  searchTrnCarUuid;
}
