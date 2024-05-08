package com.tournet.tournetERP.common.service;

import com.tournet.tournetERP.common.dto.ComCodeDTO;
import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.repository.ComCodeRepository;
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (comCodeReq.getSearchCodeKr() != null) {
            comCodeKor = comCodeReq.getSearchCodeKr();
        }
        if (comCodeReq.getSearchCodeEn() != null) {
            comCodeEng = comCodeReq.getSearchCodeEn();
        }

        List<ComCode> selectedComCodes = comCodeRepository.findAllByOrderByCodeOrd(
                uprCodeUuid == 0 ? null : uprCodeUuid,
                comCodeKor.isEmpty() ? null : comCodeKor,
                comCodeEng.isEmpty() ? null : comCodeEng
        );


        List<ComCodeDTO> comCodeResList = new ArrayList<>();
        for (ComCode comCode : selectedComCodes) {
            ComCodeDTO comCodeResponse = new ComCodeDTO();
            comCodeResponse.setCodeUuid(comCode.getCodeUuid());
            comCodeResponse.setCodeKr(comCode.getCodeKr());
            comCodeResponse.setCodeEn(comCode.getCodeEn());
            comCodeResponse.setUseYn(comCode.getUseYn());
            comCodeResponse.setCodeOrd(comCode.getCodeOrd());
            comCodeResponse.setUprCodeUuid(comCode.getUprCodeUuid());

//            if(comCode.getUprCodeUuid() != 0 && comCode.getCodeLvl() != 0){
//                comCodeResponse.setUprNameKr(fetchCodeUtil.uprFetchCodeKr(comCode.getUprCodeUuid()));
//            }
//            if(comCode.getUprCodeUuid() != 0 && comCode.getCodeLvl() != 0){
//                comCodeResponse.setUprNameEn(fetchCodeUtil.uprFetchCodeEn(comCode.getUprCodeUuid()));
//            }
            comCodeResponse.setModifiedByName(comCode.getModifyUser().getUsername());
            comCodeResponse.setCreatedByName(comCode.getCreateUser().getUsername());

            comCodeResList.add(comCodeResponse);
        }
//        List<ComCodeResponse> comCodeResList = null;
        return comCodeResList;
    }
}
