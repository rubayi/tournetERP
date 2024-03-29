package com.tournet.tournetERP.contents.service;

import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.repository.ComCodeRepository;
import com.tournet.tournetERP.contents.dto.CompanyRequest;
import com.tournet.tournetERP.contents.dto.CompanyResponse;
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
    ComCodeRepository comcodeRepository;
    public List<CompanyResponse> findCompsList(CompanyRequest compReq) {

        ModelMapper modelMapper=new ModelMapper();

        long compSector = compReq.getCompSector();
        String compKor = compReq.getCompKor();
        String compEng = compReq.getCompEng();

        List<Company> selectedCompanys = compRepository.findAllByOrderByModifiedDtDesc(
                compSector == 0 ? null : compSector,
                compKor.isEmpty() ? null : compKor,
                compEng.isEmpty() ? null : compEng
        );

        List<CompanyResponse> compResList = selectedCompanys.stream()
                .map(comp -> {
                    CompanyResponse compResponse = new CompanyResponse();
                    compResponse.setCompUuid(comp.getCompUuid());
                    compResponse.setCompKor(comp.getCompKor());
                    compResponse.setCompEng(comp.getCompEng());
                    compResponse.setLogoFile(comp.getLogoFile());
                    
                    compResponse.setCompSectorName(fetchCodeKr(comp.getCompSector()));
                    compResponse.setHotelRateName(fetchCodeKr(comp.getHotelRate()));
                    compResponse.setOptionRateName(fetchCodeKr(comp.getOptionRate()));
                    compResponse.setRentcarRateName(fetchCodeKr(comp.getRentcarRate()));
                    compResponse.setRestaurantRateName(fetchCodeKr(comp.getRestaurantRate()));
                    compResponse.setPackRegRateName(fetchCodeKr(comp.getPackRegRate()));
                    compResponse.setPackRateName(fetchCodeKr(comp.getPackRate()));
                    compResponse.setHoneymoonRegRateName(fetchCodeKr(comp.getHoneymoonRegRate()));
                    compResponse.setHoneymoonRateName(fetchCodeKr(comp.getHoneymoonRate()));

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
                    compResponse.setModifiedByName(comp.getModifyUser().getUsername());
                    compResponse.setCreatedByName(comp.getCreateUser().getUsername());
                   
                    return compResponse;
                })
                .collect(Collectors.toList());
//        List<CompanyResponse> compResList = null;
        return compResList;
    }


    public CompanyResponse findByCompUuid(long id) {
        ModelMapper modelMapper=new ModelMapper();

        CompanyResponse findCompany = modelMapper.map(compRepository.findFirstByCompUuid(id), CompanyResponse.class);

        return findCompany;
    }

    private String fetchCodeKr(long codeUuid) {
        ComCode comcode = comcodeRepository.findFirstByCodeUuid(codeUuid);
        if (comcode != null) {
            return comcode.getCodeKr();
        } else {
            return null;
        }
    }
}
