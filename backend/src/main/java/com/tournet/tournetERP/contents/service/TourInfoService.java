package com.tournet.tournetERP.contents.service;


import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.TourDTO;
import com.tournet.tournetERP.contents.entity.Tour;
import com.tournet.tournetERP.contents.repository.TourRepository;
import org.modelmapper.ModelMapper;
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
    public List<TourDTO> findtoursList(TourDTO tourpReq) {

        ModelMapper modelMapper = new ModelMapper();

        long tourCategory = tourpReq.getTourCategory();
        String tourKor = tourpReq.getTourKor();
        String tourEng = tourpReq.getTourEng();

        List<Tour> selectedTours = tourpRepository.findTourOrderByModifiedDtDesc(
                tourCategory == 0 ? null : tourCategory,
                tourKor.isEmpty() ? null : tourKor,
                tourEng.isEmpty() ? null : tourEng
        );

        List<TourDTO> tourpResList = selectedTours.stream()
                .map(tourp -> {
                    TourDTO tourpResponse = new TourDTO();
                    tourpResponse.setTourUuid(tourp.getTourUuid());
                    tourpResponse.setTourKor(tourp.getTourKor());
                    tourpResponse.setTourEng(tourp.getTourEng());

                    tourpResponse.setTourCategoryName(fetchCodeUtil.fetchCodeKr(tourp.getTourCategory()));
                    tourpResponse.setTourAreaName(fetchCodeUtil.fetchCodeKr(tourp.getTourArea()));
                    tourpResponse.setPrepaidMethodName(fetchCodeUtil.fetchCodeKr(tourp.getPrepaidMethod()));

                    tourpResponse.setMinAge(tourp.getMinAge());
                    tourpResponse.setChildAge(tourp.getChildAge());
                    tourpResponse.setYouthAge(tourp.getYouthAge());
                    tourpResponse.setModifiedByName(tourp.getModifyUser().getUsername());
                    tourpResponse.setCreatedByName(tourp.getCreateUser().getUsername());

                    tourpResponse.setCreatedDt(tourp.getCreatedDt());

                    return tourpResponse;
                })
                .collect(Collectors.toList());

        return tourpResList;
    }
}
