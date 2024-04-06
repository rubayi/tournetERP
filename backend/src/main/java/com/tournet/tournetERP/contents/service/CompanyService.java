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

import java.util.List;
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

        ModelMapper modelMapper=new ModelMapper();

        long compSector = compReq.getCompSector();
        String compKor = compReq.getCompKor();
        String compEng = compReq.getCompEng();

        List<Company> selectedCompanys = compRepository.findAllByOrderByModifiedDtDesc(
                compSector == 0 ? null : compSector,
                compKor.isEmpty() ? null : compKor,
                compEng.isEmpty() ? null : compEng
        );

        List<CompanyDTO> compResList = selectedCompanys.stream()
                .map(comp -> {
                    CompanyDTO compResponse = new CompanyDTO();
                    compResponse.setCompUuid(comp.getCompUuid());
                    compResponse.setCompKor(comp.getCompKor());
                    compResponse.setCompEng(comp.getCompEng());
                    compResponse.setLogoFile(comp.getLogoFile());
                    
                    compResponse.setCompSectorName(fetchCodeUtil.fetchCodeKr(comp.getCompSector()));
                    compResponse.setHotelRateName(fetchCodeUtil.fetchCodeKr(comp.getHotelRate()));
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
                    compResponse.setCreatedDt(comp.getCreatedDt());
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


}
