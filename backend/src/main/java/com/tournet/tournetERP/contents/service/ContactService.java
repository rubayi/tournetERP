package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ContactService
 * @since : 2024-04-09
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.ContactDTO;
import com.tournet.tournetERP.contents.entity.Contact;
import com.tournet.tournetERP.contents.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<ContactDTO> findContactList(Contact contactReq) {

        long compUuid = contactReq.getCompUuid();
        String contactCont = "";
        long contactType = 0;
        if (contactReq.getContactCont() != null) {
            contactCont = contactReq.getContactCont();
        }
        if (contactReq.getContactCont() != null) {
            contactType = contactReq.getContactType();
        }

        List<Contact> selectedContacts = contactRepository.findAllByOrderByContactUuidDesc(
                compUuid == 0 ? null : compUuid,
                !contactCont.isEmpty() ? null : contactCont,
                contactType == 0 ? null :  contactType
        );

        List<ContactDTO> compContList = selectedContacts.stream()
                .map(comp -> {
                    ContactDTO compResponse = new ContactDTO();
                    compResponse.setContactUuid(comp.getContactUuid());
                    compResponse.setCompUuid(comp.getCompUuid());
                    compResponse.setContactCont(comp.getContactCont());
                    compResponse.setContactType(comp.getContactType());

                    compResponse.setContactTypeName(fetchCodeUtil.fetchCodeKr(comp.getContactType()));

                    return compResponse;
                })
                .collect(Collectors.toList());
//        List<CompanyResponse> compResList = null;
        return compContList;
    }


}
