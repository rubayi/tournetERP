package com.tournet.tournetERP.common.service;

import com.tournet.tournetERP.common.dto.ComCodeDTO;
import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.repository.ComCodeRepository;
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ComCodeService
 * @since : 2024-04-25
 */
@Service
@Transactional
public class ComCodeService {

    @Autowired
    ComCodeRepository comCodeRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<ComCodeDTO> findComCodeList(ComCodeDTO comCodeReq) {

        long uprCodeUuid = 0;
        String comCodeKor = "";
        String comCodeEng = "";

        if (comCodeReq.getSearchUprCodeUuid() != 0) {
            uprCodeUuid = comCodeReq.getSearchUprCodeUuid();
        }
        if (comCodeReq.getCodeKr() != null) {
            comCodeKor = comCodeReq.getCodeKr();
        }
        if (comCodeReq.getCodeEn() != null) {
            comCodeEng = comCodeReq.getCodeEn();
        }

        List<ComCode> selectedComCodes = comCodeRepository.findAllByOrderByCodeOrd(
                uprCodeUuid == 0 ? null : uprCodeUuid,
                comCodeKor.isEmpty() ? null : comCodeKor,
                comCodeEng.isEmpty() ? null : comCodeEng
        );

        List<ComCodeDTO> comCodeResList = selectedComCodes.stream()
                .map(comCode -> {
                    ComCodeDTO comCodeResponse = new ComCodeDTO();
                    comCodeResponse.setCodeUuid(comCode.getCodeUuid());
                    comCodeResponse.setCodeKr(comCode.getCodeKr());
                    comCodeResponse.setCodeEn(comCode.getCodeEn());


                    comCodeResponse.setModifiedByName(comCode.getModifyUser().getUsername());
                    comCodeResponse.setCreatedByName(comCode.getCreateUser().getUsername());

                    return comCodeResponse;
                })
                .collect(Collectors.toList());
//        List<ComCodeResponse> comCodeResList = null;
        return comCodeResList;
    }
}
