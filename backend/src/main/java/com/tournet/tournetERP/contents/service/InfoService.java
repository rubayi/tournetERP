package com.tournet.tournetERP.contents.service;


import com.tournet.tournetERP.contents.entity.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.repository.InfoRepository;
import com.tournet.tournetERP.contents.dto.InfoDTO;

import java.util.stream.Collectors;
import java.util.List;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : InfoInfoService
 * @since : 2024-04-05
 */
@Service
@Transactional
public class InfoService {

    @Autowired
    InfoRepository infopRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<InfoDTO> findinfosList(InfoDTO infopReq) {

        long infoUuid = infopReq.getSearchInfoUuid();
        long tourUuid = infopReq.getSearchTourUuid();

        List<Info> selectedInfos = infopRepository.findInfoOrderByModifiedDtDesc(
                infoUuid == 0 ? null : infoUuid,
                tourUuid == 0 ? null : tourUuid
        );

        List<InfoDTO> infopResList = selectedInfos.stream()
                .map(infop -> {
                    InfoDTO infopResponse = new InfoDTO();
                    infopResponse.setInfoUuid(infop.getInfoUuid());
                    infopResponse.setContentKor(infop.getContentKor());
                    infopResponse.setContentEng(infop.getContentEng());

                    infopResponse.setInfoType(infop.getInfoType());
                    infopResponse.setInfoTypeName(fetchCodeUtil.fetchCodeKr(infop.getInfoType()));
                    infopResponse.setInfoTypeNameEn(fetchCodeUtil.fetchCodeEn(infop.getInfoType()));

                    infopResponse.setApplyBeginDt(infop.getApplyBeginDt());
                    infopResponse.setApplyEndDt(infop.getApplyEndDt());

                    infopResponse.setModifiedByName(infop.getModifiedUser().getUsername());
                    infopResponse.setCreatedByName(infop.getCreatedUser().getUsername());

                    infopResponse.setCreatedDt(infop.getCreatedDt());
                    infopResponse.setModifiedDt(infop.getModifiedDt());

                    return infopResponse;
                })
                .collect(Collectors.toList());

        return infopResList;
    }

}
