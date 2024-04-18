package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : HotelService
 * @since : 2024-04-17
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.HotelDTO;
import com.tournet.tournetERP.contents.entity.Hotel;
import com.tournet.tournetERP.contents.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public HotelDTO findHotelAddInfo(Hotel hotelReq) {
        HotelDTO hotelInfo = new HotelDTO();

        hotelInfo.setHotelUuid(hotelReq.getHotelUuid());
        hotelInfo.setTourUuid(hotelReq.getTourUuid());
        hotelInfo.setHotelLvlName(fetchCodeUtil.fetchCodeKr(hotelReq.getHotelLvl()));
        hotelInfo.setHotelLvl(hotelReq.getHotelLvl());
        hotelInfo.setHotelGrpName(fetchCodeUtil.fetchCodeKr(hotelReq.getHotelGrp()));
        hotelInfo.setHotelGrp(hotelReq.getHotelGrp());
        hotelInfo.setModifiedByName(hotelReq.getModifiedUser().getUsername());
        hotelInfo.setCreatedByName(hotelReq.getCreatedUser().getUsername());

        hotelInfo.setChildAgeBreakfast(hotelReq.getChildAgeBreakfast());
        hotelInfo.setCheckinTime(hotelReq.getCheckinTime());
        hotelInfo.setCheckoutTime(hotelReq.getCheckoutTime());
        hotelInfo.setResortFee(hotelReq.getResortFee());
        hotelInfo.setCreatedDt(hotelReq.getCreatedDt());
        hotelInfo.setModifiedDt(hotelReq.getModifiedDt());

        return hotelInfo;
    }

}