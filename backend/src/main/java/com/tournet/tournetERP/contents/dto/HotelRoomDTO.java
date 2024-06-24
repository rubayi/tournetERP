package com.tournet.tournetERP.contents.dto;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : HotelRoomDTO
 * @since : 2024-06-20
 */
import lombok.Data;

@Data
public class HotelRoomDTO {

    private long roomUuid;
    private long serviceUuid;
    private long roomInfo;
    private long infoType;

    private long searchServiceUuid;
    private long searchRoomUuid;
    private long searchInfoType;
    private long searchRoomInfo;

    private String roomInfoKor;
    private String roomInfoEng;

    private String infoTypeKor;
    private String infoTypeEng;

}