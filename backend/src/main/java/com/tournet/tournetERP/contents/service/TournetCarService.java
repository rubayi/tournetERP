package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetCarService
 * @since : 2024-06-21
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.common.util.FetchEmpUtil;
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

    @Autowired
    FetchEmpUtil fetchEmpUtil;

    public List<TournetCarDTO> findTournetCarList(TournetCarDTO tournetCarReq) {

        long trnCarUuid = tournetCarReq.getSearchTrnCarUuid();
        long carType = tournetCarReq.getSearchCarType();
        long carBrand = tournetCarReq.getSearchCarBrand();
        long people = tournetCarReq.getSearchPeople();
        long carManager = tournetCarReq.getSearchCarManager();
        String carVin = tournetCarReq.getSearchCarVin();

        List<TournetCar> trnCarList =
                tournetCarRepository.findAllByOrderByModifiedDtDesc(
                        trnCarUuid == 0 ? null : trnCarUuid,
                        carType == 0 ? null : carType,
                        carBrand == 0 ? null : carBrand,
                        people == 0 ? null : people,
                        carManager == 0 ? null : carManager,
                        carVin.isEmpty() ? null : carVin
                );
        List<TournetCarDTO> trnCarInfoList = trnCarList.stream()
                .map(trnCar -> {
                    TournetCarDTO trnCarInfo = new TournetCarDTO();

                    trnCarInfo.setTrnCarUuid(trnCar.getTrnCarUuid());
                    trnCarInfo.setCarType(trnCar.getCarType());
                    trnCarInfo.setCarBrand(trnCar.getCarBrand());
                    trnCarInfo.setCarVin(trnCar.getCarVin());
                    trnCarInfo.setPeople(trnCar.getPeople());
                    trnCarInfo.setCarYear(trnCar.getCarYear());
                    trnCarInfo.setLicensePlate(trnCar.getLicensePlate());
                    trnCarInfo.setCarEndDt(trnCar.getCarEndDt());
                    trnCarInfo.setCarTypeKor(fetchCodeUtil.fetchCodeKr(trnCar.getCarType()));
                    trnCarInfo.setCarTypeEng(fetchCodeUtil.fetchCodeEn(trnCar.getCarType()));
                    trnCarInfo.setCarBrandKor(fetchCodeUtil.fetchCodeKr(trnCar.getCarBrand()));
                    trnCarInfo.setCarBrandEng(fetchCodeUtil.fetchCodeEn(trnCar.getCarBrand()));
                    trnCarInfo.setCarManagerNameKor(fetchEmpUtil.fetchEmpKor(trnCar.getCarManager()));
                    trnCarInfo.setCarManagerNameEng(fetchEmpUtil.fetchEmpEng(trnCar.getCarManager()));
                    trnCarInfo.setModifiedByName(trnCar.getModifyUser().getUsername());
                    trnCarInfo.setCreatedByName(trnCar.getCreateUser().getUsername());

                    return trnCarInfo;
                })
                .collect(Collectors.toList());

        return trnCarInfoList;
    }


}