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
import com.tournet.tournetERP.contents.entity.Tour;
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


    public List<HotelDTO> findHotelList(HotelDTO searchhotelReq) {

        long hotelUuid = searchhotelReq.getSearchHotelUuid();
        long tourUuid = searchhotelReq.getSearchTourUuid();
        long hotelGrp = searchhotelReq.getSearchHotelGrp();


        List<Hotel> selectedTours = hotelRepository.findHotelOrderByModifiedDtDesc(
                hotelUuid == 0 ? null : hotelUuid,
                tourUuid == 0 ? null : tourUuid,
                hotelGrp == 0 ? null : hotelGrp
        );

        List<HotelDTO> hotelpResList = selectedTours.stream()
                .map(hotelp -> {
                    HotelDTO hotelResponse = new HotelDTO();
                    hotelResponse.setTourUuid(hotelp.getTourUuid());

                    hotelResponse.setHotelUuid(hotelp.getHotelUuid());
                    hotelResponse.setHotelLvl(hotelp.getHotelLvl());
                    hotelResponse.setHotelGrp(hotelp.getHotelGrp());

                    hotelResponse.setHotelLvlName(fetchCodeUtil.fetchCodeKr(hotelp.getHotelLvl()));
                    hotelResponse.setHotelGrpName(fetchCodeUtil.fetchCodeKr(hotelp.getHotelGrp()));

                    hotelResponse.setChildAgeBreakfast(hotelp.getChildAgeBreakfast());
                    hotelResponse.setCheckinTime(hotelp.getCheckinTime());
                    hotelResponse.setCheckoutTime(hotelp.getCheckoutTime());
                    hotelResponse.setResortFee(hotelp.getResortFee());

                    hotelResponse.setModifiedByName(hotelp.getModifiedUser().getUsername());
                    hotelResponse.setCreatedByName(hotelp.getCreatedUser().getUsername());

                    hotelResponse.setCreatedDt(hotelp.getCreatedDt());
                    hotelResponse.setModifiedDt(hotelp.getModifiedDt());

                    return hotelResponse;
                })
                .collect(Collectors.toList());

        return hotelpResList;
    }
}