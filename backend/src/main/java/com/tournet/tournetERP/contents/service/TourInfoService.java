package com.tournet.tournetERP.contents.service;


import com.tournet.tournetERP.account.entity.CreditCardMng;
import com.tournet.tournetERP.account.repository.CreditCardMngRepository;
import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.TourDTO;
import com.tournet.tournetERP.contents.entity.Tour;
import com.tournet.tournetERP.contents.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TourInfoService
 * @since : 2024-04-05
 */
@Service
@Transactional
public class TourInfoService {

    @Autowired
    TourRepository tourpRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    @Autowired
    CompanyService companyService;

    @Autowired
    CreditCardMngRepository creditCardMngRepository;

    public List<TourDTO> findtoursList(TourDTO tourpReq) {

        long tourCategory = tourpReq.getSearchTourCategory();
        long tourUuid = tourpReq.getSearchTourUuid();
        long tourArea = tourpReq.getSearchTourArea();
        long tourAreaSub = tourpReq.getSearchTourAreaSub();
        long prepaidMethod = tourpReq.getSearchPrepaidMethod();

        String tourKor = tourpReq.getSearchTourKor();
        String tourEng = tourpReq.getSearchTourEng();

        List<Tour> selectedTours = tourpRepository.findTourOrderByModifiedDtDesc(
                tourCategory == 0 ? null : tourCategory,
                tourKor.isEmpty() ? null : tourKor,
                tourEng.isEmpty() ? null : tourEng,
                tourUuid == 0 ? null : tourUuid,
                tourArea == 0 ? null : tourArea,
                tourAreaSub == 0 ? null : tourAreaSub,
                prepaidMethod == 0 ? null : prepaidMethod
        );

        List<TourDTO> tourpResList = selectedTours.stream()
                .map(tourp -> {
                    TourDTO tourpResponse = new TourDTO();
                    tourpResponse.setTourUuid(tourp.getTourUuid());
                    tourpResponse.setTourKor(tourp.getTourKor());
                    tourpResponse.setTourEng(tourp.getTourEng());

                    tourpResponse.setTourCategory(tourp.getTourCategory());
                    tourpResponse.setTourArea(tourp.getTourArea());
                    tourpResponse.setTourAreaSub(tourp.getTourAreaSub());
                    tourpResponse.setPrepaidMethod(tourp.getPrepaidMethod());

                    tourpResponse.setTourCategoryName(fetchCodeUtil.fetchCodeKr(tourp.getTourCategory()));
                    tourpResponse.setTourAreaName(fetchCodeUtil.fetchCodeKr(tourp.getTourArea()));
                    tourpResponse.setTourAreaSubName(fetchCodeUtil.fetchCodeKr(tourp.getTourAreaSub()));
                    tourpResponse.setPrepaidMethodName(fetchPrepaidKr(tourp.getPrepaidMethod()));

                    tourpResponse.setTourCategoryNameEn(fetchCodeUtil.fetchCodeEn(tourp.getTourCategory()));
                    tourpResponse.setTourAreaNameEn(fetchCodeUtil.fetchCodeEn(tourp.getTourArea()));
                    tourpResponse.setTourAreaSubNameEn(fetchCodeUtil.fetchCodeEn(tourp.getTourAreaSub()));
                    tourpResponse.setPrepaidMethodNameEn(fetchPrepaidEn(tourp.getPrepaidMethod()));

                    tourpResponse.setMinAge(tourp.getMinAge());
                    tourpResponse.setChildAge(tourp.getChildAge());
                    tourpResponse.setYouthAge(tourp.getYouthAge());

                    tourpResponse.setBeginDt(tourp.getBeginDt());
                    tourpResponse.setEndDt(tourp.getEndDt());

                    tourpResponse.setModifiedByName(tourp.getModifyUser().getUsername());
                    tourpResponse.setCreatedByName(tourp.getCreateUser().getUsername());

                    tourpResponse.setCreatedDt(tourp.getCreatedDt());
                    tourpResponse.setModifiedDt(tourp.getModifiedDt());

                    return tourpResponse;
                })
                .collect(Collectors.toList());

        return tourpResList;
    }


    public String fetchPrepaidKr(long codeUuid) {
        CreditCardMng cdcdCode = creditCardMngRepository.findOneByCdCdUuid(codeUuid);
        if (cdcdCode != null) {
            return cdcdCode.getMngNameKor();
        } else {
            return null;
        }
    }

    public String fetchPrepaidEn(long codeUuid) {
        CreditCardMng cdcdCode = creditCardMngRepository.findOneByCdCdUuid(codeUuid);
        if (cdcdCode != null) {
            return cdcdCode.getMngNameEng();
        } else {
            return null;
        }
    }
}
