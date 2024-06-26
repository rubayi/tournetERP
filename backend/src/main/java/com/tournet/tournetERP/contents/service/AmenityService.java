package com.tournet.tournetERP.contents.service;
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.AmenityDTO;
import com.tournet.tournetERP.contents.entity.Amenity;
import com.tournet.tournetERP.contents.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AmenityService {
    
    @Autowired
    AmenityRepository amenityRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<AmenityDTO> findAmenityList(AmenityDTO searchReq) {
        long amenityUuid = searchReq.getSearchAmenityUuid();
        long tourUuid = searchReq.getSearchTourUuid();

        List<Amenity> amenityList = amenityRepository.findAmenityOrderByModifiedDtDesc(
                amenityUuid == 0 ? null : amenityUuid,
                tourUuid == 0 ? null : tourUuid);

        List<AmenityDTO> amenityResList = amenityList.stream()
                .map(amenity -> {
                    AmenityDTO amenityInfo = new AmenityDTO();
                    amenityInfo.setAmenityUuid(amenity.getAmenityUuid());
                    amenityInfo.setTourUuid(amenity.getTourUuid());
                    amenityInfo.setAmenityTypeKor(fetchCodeUtil.fetchCodeKr(amenity.getAmenityType()));
                    amenityInfo.setAmenityTypeEng(fetchCodeUtil.fetchCodeEn(amenity.getAmenityType()));
                    amenityInfo.setAmenityDescKor(amenity.getAmenityDescKor());
                    amenityInfo.setAmenityDescEng(amenity.getAmenityDescEng());
                    amenityInfo.setModifiedByName(amenity.getModifiedUser().getUsername());
                    amenityInfo.setCreatedByName(amenity.getCreatedUser().getUsername());
                    amenityInfo.setCreatedDt(amenity.getCreatedDt());
                    amenityInfo.setModifiedDt(amenity.getModifiedDt());
                    return amenityInfo;
                })
                .collect(Collectors.toList());
              
        return amenityResList;
    }
}
