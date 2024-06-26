package com.tournet.tournetERP.account.service;

import com.tournet.tournetERP.account.dto.CreditCardMngDTO;
import com.tournet.tournetERP.account.entity.CreditCardMng;
import com.tournet.tournetERP.account.repository.CreditCardMngRepository;
import com.tournet.tournetERP.common.util.FetchCodeUtil;

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
 * @fileName : CreditCardMngService
 * @since : 2024-03-14
 */
@Service
@Transactional
public class CreditCardMngService {

    @Autowired
    CreditCardMngRepository cdcdMngRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;
    public List<CreditCardMngDTO> findCreditCardMngList(CreditCardMngDTO compReq) {

        ModelMapper modelMapper=new ModelMapper();

        long cdcdUuid = compReq.getSearchCdCdUuid();
        String mngNameKor = compReq.getSearchMngNameKor();
        String mngNameEng = compReq.getSearchMngNameEng();
        String cardNumber = compReq.getSearchCardNumber();

        List<CreditCardMng> selectedCreditCardMngs = cdcdMngRepository.findAllByOrderByModifiedDtDesc(
                cdcdUuid == 0 ? null : cdcdUuid,
                mngNameKor.isEmpty() ? null : mngNameKor,
                mngNameEng.isEmpty() ? null : mngNameEng,
                cardNumber.isEmpty() ? null : cardNumber
        );

        List<CreditCardMngDTO> compResList = selectedCreditCardMngs.stream()
                .map(comp -> {
                    CreditCardMngDTO compResponse = new CreditCardMngDTO();
                    compResponse.setCdCdUuid(comp.getCdCdUuid());
                    compResponse.setMngNameKor(comp.getMngNameKor());
                    compResponse.setMngNameEng(comp.getMngNameEng());

                    compResponse.setNameOnCard(comp.getNameOnCard());
                    compResponse.setExpMonth(comp.getExpMonth());
                    compResponse.setExpYear(comp.getExpYear());
                    compResponse.setCardNumber(comp.getCardNumber());

                    compResponse.setBeginDt(comp.getBeginDt());
                    compResponse.setEndDt(comp.getEndDt());
                    compResponse.setCreatedDt(comp.getCreatedDt());
                    compResponse.setModifiedDt(comp.getModifiedDt());
                    compResponse.setModifiedByName(comp.getModifyUser().getUsername());
                    compResponse.setCreatedByName(comp.getCreateUser().getUsername());

                    return compResponse;
                })
                .collect(Collectors.toList());
//        List<CreditCardMngResponse> compResList = null;
        return compResList;
    }

}
