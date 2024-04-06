package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : CompanyConverter
 * @since : 2024-04-05
 */
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.contents.dto.CompanyDTO;
import com.tournet.tournetERP.contents.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverter {

    public Company convertToEntity(CompanyDTO companyRequest) {
        Company company = new Company();
        User modifyingUser = new User();
        modifyingUser.setEmpUuid(companyRequest.getCompUuid());

        company.setCompUuid(companyRequest.getCompUuid());
        company.setCompSector(companyRequest.getCompSector());
        company.setCompGroup(companyRequest.getCompGroup());
        company.setCompKor(companyRequest.getCompKor());
        company.setCompEng(companyRequest.getCompEng());
        company.setCompAbb(companyRequest.getCompAbb());
        company.setCompColor(companyRequest.getCompColor());
        company.setLogoFile(companyRequest.getLogoFile());
        company.setEstDate(companyRequest.getEstDate());
        company.setCompRate(companyRequest.getCompRate());
        company.setHotelRate(companyRequest.getHotelRate());
        company.setOptionRate(companyRequest.getOptionRate());
        company.setRentcarRate(companyRequest.getRentcarRate());
        company.setRestaurantRate(companyRequest.getRestaurantRate());
        company.setPackRegRate(companyRequest.getPackRegRate());
        company.setPackRate(companyRequest.getPackRate());
        company.setHoneymoonRegRate(companyRequest.getHoneymoonRegRate());
        company.setHoneymoonRate(companyRequest.getHoneymoonRate());
        company.setMinAge(companyRequest.getMinAge());
        company.setChildAge(companyRequest.getChildAge());
        company.setCouponYn(companyRequest.getCouponYn());
        company.setYouthAge(companyRequest.getYouthAge());
        company.setPrepaidHow(companyRequest.getPrepaidHow());
        company.setBeginDt(companyRequest.getBeginDt());
        company.setEndDt(companyRequest.getEndDt());
        company.setCreatedDt(companyRequest.getCreatedDt());
        company.setCreateUser(modifyingUser);
        company.setModifyUser(modifyingUser);
        company.setModifiedDt(companyRequest.getModifiedDt());
        return company;
    }

}
