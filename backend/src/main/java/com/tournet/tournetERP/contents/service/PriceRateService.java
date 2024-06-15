package com.tournet.tournetERP.contents.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : PriceRateService
 * @since : 2024-06-14
 */
import com.tournet.tournetERP.common.util.FetchCodeUtil;
import com.tournet.tournetERP.contents.dto.PriceRateDTO;
import com.tournet.tournetERP.contents.entity.PriceRate;
import com.tournet.tournetERP.contents.repository.PriceRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PriceRateService {

    @Autowired
    PriceRateRepository priceRateRepository;

    @Autowired
    FetchCodeUtil fetchCodeUtil;

    public List<PriceRateDTO> findPriceRateList(PriceRate priceRateReq) {
        List<PriceRate> selectedPriceRates = priceRateRepository.findAllByOrderByRateUuidDesc(
        );
        return null;
    }


}
