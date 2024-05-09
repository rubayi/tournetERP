package com.tournet.tournetERP.contents.service;

import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.repository.ComCodeRepository;
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.CompanyDTO;
import com.tournet.tournetERP.contents.entity.Company;
import com.tournet.tournetERP.contents.repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 업체관리
 *
 * @author : rubayi
 * @fileName : CompanyService
 * @since : 2024-03-14
 */
@Service
@Transactional
public class CompanyService {

    @Autowired
    CompanyRepository compRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;
    public List<CompanyDTO> findCompsList(CompanyDTO compReq) {

        long compSector = 0;
        String compKor = "";
        String compEng = "";
        String compMemo = "";
        Date beginDt = null;
        Date endDt = null;

        long compGroup = 0;
        long couponYn = 0;
        long compRate = 0;
        long compUuid = 0;
        
        if (compReq.getSearchCompSector() != 0) {
            compSector = compReq.getSearchCompSector();
        }
        if (compReq.getSearchCompRate() != 0) {
            compRate = compReq.getSearchCompRate();
        }
        if (compReq.getSearchCompUuid() != 0) {
            compUuid = compReq.getSearchCompUuid();
        }
        if (compReq.getSearchCouponYn() != 0) {
            couponYn = compReq.getSearchCouponYn();
        }
        if (compReq.getSearchCompGroup() != 0) {
            compGroup = compReq.getSearchCompGroup();
        }
        if (compReq.getSearchCompKor() != null) {
            compKor = compReq.getSearchCompKor();
        }
        if (compReq.getSearchCompEng() != null) {
            compEng = compReq.getSearchCompEng();
        }
        if (compReq.getSearchBeginDt() != null) {
            beginDt = compReq.getSearchBeginDt();
        }
        if (compReq.getSearchEndDt() != null) {
            endDt = compReq.getSearchEndDt();
        }
        if (compReq.getSearchCompMemo() != null) {
            compMemo = compReq.getSearchCompMemo();
        }

        List<Company> selectedCompanys = compRepository.findAllByOrderByModifiedDtDesc(
                compSector == 0 ? null : compSector,
                compUuid == 0 ? null : compUuid,
                couponYn == 0 ? null : couponYn,
                compGroup == 0 ? null : compGroup,
                compRate == 0 ? null : compRate,

                compKor.isEmpty() ? null : compKor,
                compEng.isEmpty() ? null : compEng,
                beginDt,
                endDt,
                compMemo.isEmpty() ? null : compMemo
        );

        List<CompanyDTO> compResList = selectedCompanys.stream()
                .map(comp -> {
                    CompanyDTO compResponse = new CompanyDTO();
                    compResponse.setCompUuid(comp.getCompUuid());
                    compResponse.setCompKor(comp.getCompKor());
                    compResponse.setCompEng(comp.getCompEng());
                    compResponse.setLogoFile(comp.getLogoFile());

                    compResponse.setCompGroupName(fetchCodeUtil.fetchCodeKr(comp.getCompGroup()));
                    compResponse.setCompGroupNameEn(fetchCodeUtil.fetchCodeEn(comp.getCompGroup()));
                    compResponse.setCompSectorName(fetchCodeUtil.fetchCodeKr(comp.getCompSector()));
                    compResponse.setCompSectorNameEn(fetchCodeUtil.fetchCodeEn(comp.getCompSector()));
                    compResponse.setHotelRateName(fetchCodeUtil.fetchCodeKr(comp.getHotelRate()));
                    compResponse.setHotelRateNameEn(fetchCodeUtil.fetchCodeEn(comp.getHotelRate()));
                    compResponse.setOptionRateName(fetchCodeUtil.fetchCodeKr(comp.getOptionRate()));
                    compResponse.setRentcarRateName(fetchCodeUtil.fetchCodeKr(comp.getRentcarRate()));
                    compResponse.setRestaurantRateName(fetchCodeUtil.fetchCodeKr(comp.getRestaurantRate()));
                    compResponse.setPackRegRateName(fetchCodeUtil.fetchCodeKr(comp.getPackRegRate()));
                    compResponse.setPackRateName(fetchCodeUtil.fetchCodeKr(comp.getPackRate()));
                    compResponse.setHoneymoonRegRateName(fetchCodeUtil.fetchCodeKr(comp.getHoneymoonRegRate()));
                    compResponse.setHoneymoonRateName(fetchCodeUtil.fetchCodeKr(comp.getHoneymoonRate()));

                    compResponse.setHotelRate(comp.getHotelRate());
                    compResponse.setOptionRate(comp.getOptionRate());
                    compResponse.setRentcarRate(comp.getRentcarRate());
                    compResponse.setRestaurantRate(comp.getRestaurantRate());
                    compResponse.setPackRegRate(comp.getPackRegRate());
                    compResponse.setPackRate(comp.getPackRate());
                    compResponse.setHoneymoonRegRate(comp.getHoneymoonRegRate());
                    compResponse.setHoneymoonRate(comp.getHoneymoonRate());

                    compResponse.setCompAbb(comp.getCompAbb());
                    compResponse.setCompSector(comp.getCompSector());

                    compResponse.setMinAge(comp.getMinAge());
                    compResponse.setChildAge(comp.getChildAge());
                    compResponse.setYouthAge(comp.getYouthAge());
                    compResponse.setCouponYn(comp.getCouponYn());
                    compResponse.setPrepaidHow(comp.getPrepaidHow());

                    compResponse.setCompColor(comp.getCompColor());

                    compResponse.setBeginDt(comp.getBeginDt());
                    compResponse.setEndDt(comp.getEndDt());
                    compResponse.setCreatedDt(comp.getCreatedDt());
                    compResponse.setModifiedDt(comp.getModifiedDt());
                    compResponse.setEstDate(comp.getEstDate());
                    compResponse.setModifiedByName(comp.getModifyUser().getUsername());
                    compResponse.setCreatedByName(comp.getCreateUser().getUsername());
                   
                    return compResponse;
                })
                .collect(Collectors.toList());
//        List<CompanyResponse> compResList = null;
        return compResList;
    }


    public CompanyDTO findByCompUuid(long id) {
        ModelMapper modelMapper=new ModelMapper();

        CompanyDTO findCompany = modelMapper.map(compRepository.findFirstByCompUuid(id), CompanyDTO.class);

        return findCompany;
    }


    public String fetchCompKr(long compUuid) {
        Optional<Company> compInfo = compRepository.findByCompUuid(compUuid);
        if (compInfo.isPresent()) {
            return compInfo.get().getCompKor();
        } else {
            return null;
        }
    }

    public String fetchCompEng(long compUuid) {
        Optional<Company> compInfo = compRepository.findByCompUuid(compUuid);
        if (compInfo.isPresent()) {
            return compInfo.get().getCompEng();
        } else {
            return null;
        }
    }

}
