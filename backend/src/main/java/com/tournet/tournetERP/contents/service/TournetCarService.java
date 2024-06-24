package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetCarService
 * @since : 2024-06-21
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.TournetCarDTO;
import com.tournet.tournetERP.contents.dto.TournetCarDTO;
import com.tournet.tournetERP.contents.entity.TournetCar;
import com.tournet.tournetERP.contents.repository.TournetCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TournetCarService {

    @Autowired
    TournetCarRepository tournetCarRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<TournetCarDTO> findTournetCarList(TournetCarDTO tournetCarReq) {

        long trnCarUuid = tournetCarReq.getSearchTrnCarUuid();
        long carType = tournetCarReq.getSearchCarType();

        List<TournetCar> trnCarList =
                tournetCarRepository.findAllByOrderByModifiedDtDesc(
                        trnCarUuid == 0 ? null : trnCarUuid,
                        carType == 0 ? null : carType
                );
        List<TournetCarDTO> trnCarInfoList = trnCarList.stream()
                .map(trnCar -> {
                    TournetCarDTO trnCarInfo = new TournetCarDTO();

                    trnCarInfo.setTrnCarUuid(trnCar.getTrnCarUuid());
                    trnCarInfo.setCarType(trnCar.getCarType());
                    trnCarInfo.setCarBrand(trnCar.getCarBrand());
                    trnCarInfo.setCarVin(trnCar.getCarVin());

                    trnCarInfo.setCarTypeKor(fetchCodeUtil.fetchCodeKr(trnCar.getCarType()));
                    trnCarInfo.setCarTypeEng(fetchCodeUtil.fetchCodeEn(trnCar.getCarType()));

                    trnCarInfo.setCarBrandKor(fetchCodeUtil.fetchCodeKr(trnCar.getCarBrand()));
                    trnCarInfo.setCarBrandEng(fetchCodeUtil.fetchCodeEn(trnCar.getCarBrand()));

                    return trnCarInfo;
                })
                .collect(Collectors.toList());

        return trnCarInfoList;
    }


}