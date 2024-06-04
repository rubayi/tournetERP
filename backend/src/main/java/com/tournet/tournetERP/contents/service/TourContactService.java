package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TourContactService
 * @since : 2024-04-09
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.TourContactDTO;
import com.tournet.tournetERP.contents.entity.TourContact;
import com.tournet.tournetERP.contents.repository.TourContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TourContactService {

    @Autowired
    TourContactRepository contactRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<TourContactDTO> findTourContactList(TourContact contactReq) {

        long tourUuid = contactReq.getTourUuid();
        String contactCont = "";
        long contactType = 0;
        if (contactReq.getContactCont() != null) {
            contactCont = contactReq.getContactCont();
        }
        if (contactReq.getContactType() != 0) {
            contactType = contactReq.getContactType();
        }

        List<TourContact> selectedTourContacts = contactRepository.findAllByOrderByContactUuidDesc(
                tourUuid == 0 ? null : tourUuid,
                !contactCont.isEmpty() ? null : contactCont,
                contactType == 0 ? null :  contactType
        );

        List<TourContactDTO> tourContList = selectedTourContacts.stream()
                .map(trCont -> {
                    TourContactDTO trContRes = new TourContactDTO();
                    trContRes.setContactUuid(trCont.getContactUuid());
                    trContRes.setTourUuid(trCont.getTourUuid());
                    trContRes.setContactCont(trCont.getContactCont());
                    trContRes.setContactType(trCont.getContactType());
                    trContRes.setRepYn(trCont.getRepYn());
                    trContRes.setRepYnName(fetchCodeUtil.fetchCodeKr(trCont.getRepYn()));
                    trContRes.setRepYnNameEn(fetchCodeUtil.fetchCodeEn(trCont.getRepYn()));
                    trContRes.setContactTypeName(fetchCodeUtil.fetchCodeKr(trCont.getContactType()));
                    trContRes.setContactTypeNameEn(fetchCodeUtil.fetchCodeEn(trCont.getContactType()));

                    return trContRes;
                })
                .collect(Collectors.toList());
//        List<CompanyResponse> compResList = null;
        return tourContList;
    }


}
