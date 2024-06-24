package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : HotelRoomService
 * @since : 2024-06-20
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.HotelRoomDTO;
import com.tournet.tournetERP.contents.dto.HotelRoomDTO;
import com.tournet.tournetERP.contents.entity.HotelRoom;
import com.tournet.tournetERP.contents.repository.HotelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class HotelRoomService {

    @Autowired
    HotelRoomRepository hotelRoomRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<HotelRoomDTO> findHotelRoomList(HotelRoomDTO hotelRoomReq) {

        long serviceUuid = hotelRoomReq.getSearchServiceUuid();
        long roomUuid = hotelRoomReq.getSearchRoomUuid();
        long infoType = hotelRoomReq.getSearchInfoType();
        long roomInfo = hotelRoomReq.getSearchRoomInfo();


        List<HotelRoom> hotelRoomList =
                hotelRoomRepository.findAllByOrderByRoomUuidDesc(
                roomUuid == 0 ? null : roomUuid,
                infoType == 0 ? null : infoType,
                roomInfo == 0 ? null : roomInfo,
                serviceUuid == 0 ? null : serviceUuid
               );
        
        List<HotelRoomDTO> hotelInfoList = hotelRoomList.stream()
                .map(hotelRoom -> {
                    HotelRoomDTO hotelInfo = new HotelRoomDTO();

                    hotelInfo.setRoomUuid(hotelRoom.getRoomUuid());
                    hotelInfo.setServiceUuid(hotelRoom.getServiceUuid());
                    hotelInfo.setInfoType(hotelRoom.getInfoType());
                    hotelInfo.setRoomInfo(hotelRoom.getRoomInfo());

                    hotelInfo.setRoomInfoKor(fetchCodeUtil.fetchCodeKr(hotelRoom.getRoomInfo()));
                    hotelInfo.setRoomInfoEng(fetchCodeUtil.fetchCodeEn(hotelRoom.getRoomInfo()));

                    hotelInfo.setInfoTypeKor(fetchCodeUtil.fetchCodeKr(hotelRoom.getInfoType()));
                    hotelInfo.setInfoTypeEng(fetchCodeUtil.fetchCodeEn(hotelRoom.getInfoType()));

                    return hotelInfo;
                })
                .collect(Collectors.toList());

        return hotelInfoList;
    }


}
