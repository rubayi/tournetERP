package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetImgService
 * @since : 2024-06-25
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.TournetImgDTO;
import com.tournet.tournetERP.contents.dto.TournetImgDTO;
import com.tournet.tournetERP.contents.entity.TournetImg;
import com.tournet.tournetERP.contents.repository.TournetImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TournetImgService {

    @Autowired
    TournetImgRepository tournetImgRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<TournetImgDTO> findTournetImgList(TournetImgDTO tournetImgReq) {

        long trnCarUuid = tournetImgReq.getSearchTrnCarUuid();

        List<TournetImg> trnImgList = tournetImgRepository.findAllByOrderByImgOrdAsc(
                trnCarUuid == 0 ? null : trnCarUuid);

        List<TournetImgDTO> trnCarInfoList = trnImgList.stream()
                .map(trnCar -> {
                    TournetImgDTO trnCarInfo = new TournetImgDTO();

                    trnCarInfo.setTrnCarUuid(trnCar.getTrnCarUuid());
                    trnCarInfo.setImgFile(trnCar.getImgFile());
                    trnCarInfo.setImgOrd(trnCar.getImgOrd());

                    return trnCarInfo;
                })
                .collect(Collectors.toList());

        return trnCarInfoList;
    }


}