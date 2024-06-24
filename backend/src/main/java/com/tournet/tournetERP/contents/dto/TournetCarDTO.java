package com.tournet.tournetERP.contents.dto;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetCarDTO
 * @since : 2024-06-21
 */
import lombok.Data;
import java.util.Date;

@Data
public class TournetCarDTO {

    private long trnCarUuid;
    private long carType;
    private long carBrand;
    private String carVin;
    private String licensePlate;
    private long people;
    private String carYear;
    private long carManager;
    private Date carEndDt;
    private String memo;
    private Date createdDt;
    private long createdBy;
    private long modifiedBy;
    private Date modifiedDt;

    private String carTypeKor;
    private String carTypeEng;

    private String carBrandKor;
    private String carBrandEng;

    private long searchTrnCarUuid;
    private long searchCarType;

}
