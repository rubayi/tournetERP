package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TourServiceService
 * @since : 2024-06-09
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.TourServiceDTO;
import com.tournet.tournetERP.contents.dto.TourServiceDTO;
import com.tournet.tournetERP.contents.entity.TourService;
import com.tournet.tournetERP.contents.repository.TourServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class TourServiceService {

    @Autowired
    TourServiceRepository serviceRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<TourServiceDTO> findTourService(TourServiceDTO serviceReq) {

        long serviceUuid = serviceReq.getSearchServiceUuid();
        long tourUuid = serviceReq.getSearchTourUuid();
        String serviceNameKor = serviceReq.getSearchServiceNameKor();
        String serviceNameEng = serviceReq.getSearchServiceNameEng();
        long pickupYn = serviceReq.getSearchPickupYn();
        long mealYn = serviceReq.getSearchMealYn();
        
        List<TourService> selectedServices = serviceRepository.findAllByOrderByModifiedDtDesc(
                serviceUuid == 0 ? null : serviceUuid,
                serviceNameKor.isEmpty() ? null : serviceNameKor,
                serviceNameEng.isEmpty() ? null : serviceNameEng,
                tourUuid == 0 ? null : tourUuid,
                pickupYn == 0 ? null : pickupYn,
                mealYn == 0 ? null : mealYn
        );
        List<TourServiceDTO> tourpResList = selectedServices.stream()
                .map(tourp -> {

                    TourServiceDTO tourpResponse = new TourServiceDTO();
                    tourpResponse.setTourUuid(tourp.getTourUuid());
                    tourpResponse.setServiceUuid(tourp.getServiceUuid());
                    tourpResponse.setServiceNameKor(tourp.getServiceNameKor());
                    tourpResponse.setServiceNameEng(tourp.getServiceNameEng());

                    tourpResponse.setPickupYn(tourp.getPickupYn());
                    tourpResponse.setServicePrice(tourp.getServicePrice());
                    tourpResponse.setMealYn(tourp.getMealYn());
                    tourpResponse.setAdults(tourp.getAdults());

                    tourpResponse.setApi(tourp.getApi());
                    tourpResponse.setServiceColor(tourp.getServiceColor());

                    tourpResponse.setPickupYnName(fetchCodeUtil.fetchCodeKr(tourp.getPickupYn()));
                    tourpResponse.setMealYnName(fetchCodeUtil.fetchCodeKr(tourp.getMealYn()));

                    tourpResponse.setChildren(tourp.getChildren());
                    tourpResponse.setMaxPeople(tourp.getMaxPeople());
                    tourpResponse.setShowingOrder(tourp.getShowingOrder());

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

}